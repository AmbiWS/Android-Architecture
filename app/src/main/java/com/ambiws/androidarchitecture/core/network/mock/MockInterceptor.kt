package com.ambiws.androidarchitecture.core.network.mock

import com.ambiws.androidarchitecture.BuildConfig
import com.ambiws.androidarchitecture.core.network.adapters.model.StatusCode
import com.ambiws.androidarchitecture.features.lists.user.data.response.UserResponse
import com.ambiws.androidarchitecture.utils.logd
import com.ambiws.androidarchitecture.utils.loge
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import kotlin.random.Random

/**
Class for mock network requests

All thrown exceptions must be subclass of IOException, so coroutines can handle them properly
 */
class MockInterceptor : Interceptor {

    private val mockResponses = mutableListOf<MockResponse>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val mockResponse = findMockResponseInList(request)
            ?: throw IOException("No mock response found for url ${request.url}.")

        removeIfNotPersisted(mockResponse)
        applyNetworkDelay(mockResponse)

        return if (mockResponse.status < StatusCode.BAD_REQUEST.code) {
            if (mockResponse.errorFrequencyInPercent <= 0) {
                createSuccessResponse(mockResponse, request)
            } else {
                createUnknownResponse(mockResponse, request)
            }
        } else {
            createErrorResponse(
                request,
                code = mockResponse.status,
                errorBody = mockResponse.body
            )
        }
    }

    private fun retrieveQueryParams(requestUrl: String): MockQueryParams {
        val offset = findParameterValue(requestUrl, QUERY_OFFSET_KEY)?.toIntOrNull()
        val amount = findParameterValue(requestUrl, QUERY_AMOUNT_KEY)?.toIntOrNull()
        val query = findParameterValue(requestUrl, QUERY_SEARCH_KEY)
        return MockQueryParams(offset, amount, query)
    }

    private fun findMockResponseInList(request: Request): MockResponse? {
        return mockResponses.find { mockResponse ->
            mockResponse.path.contains(request.url.encodedPath)
        }
    }

    private fun removeIfNotPersisted(mockResponse: MockResponse) {
        if (!mockResponse.persist) {
            mockResponses.remove(mockResponse)
        }
    }

    private fun applyNetworkDelay(mockResponse: MockResponse) {
        Thread.sleep(mockResponse.delayInMs)
    }

    private fun createUnknownResponse(
        mockResponse: MockResponse,
        request: Request
    ): Response {
        val maxPercentage = 100
        if (mockResponse.errorFrequencyInPercent > maxPercentage) {
            throw IOException("Error frequency in percent cannot exceed 100.")
        }
        return when (Random.nextInt(0, maxPercentage + 1)) {
            in 0..mockResponse.errorFrequencyInPercent -> createErrorResponse(request)
            else -> createSuccessResponse(mockResponse, request)
        }
    }

    private fun createSuccessResponse(mockResponse: MockResponse, request: Request): Response {
        val queryParams = retrieveQueryParams(request.url.toString())
        val parsedBody = if (mockResponse.responseClass != null) {
            applyQueryParams(
                mockResponse.body,
                mockResponse.responseClass,
                queryParams,
                request.url.toString()
            )
        } else {
            if (queryParams.isNotNull()) {
                loge("Response class must be defined to use query on mocked requests!")
            }
            mockResponse.body
        }

        return Response.Builder()
            .code(mockResponse.status)
            .request(request)
            .protocol(Protocol.HTTP_2)
            .message("OK")
            .body(
                parsedBody.toResponseBody("application/json".toMediaType())
            )
            .build()
    }

    private fun <T> applyQueryParams(
        rawList: List<T>,
        queryParams: MockQueryParams,
        searchPredicate: ((T) -> (Boolean))? = null
    ): String {
        var updatedList = rawList
        if (queryParams.query != null && searchPredicate != null) updatedList = updatedList.filter { searchPredicate(it) }
        if (queryParams.offset != null) updatedList = updatedList.subList(queryParams.offset, updatedList.size)
        if (queryParams.amount != null) updatedList = updatedList.take(queryParams.amount)
        return Gson().toJson(updatedList)
    }

    private fun applyQueryParams(
        body: String,
        responseClass: MockNetworkResponseClass,
        queryParams: MockQueryParams,
        requestUrl: String,
    ): String {
        // Accepting only json arrays for parsing
        if (!body.startsWith('[')) return body
        val gson = Gson()
        return when (responseClass) {
            MockNetworkResponseClass.USER -> {
                if (responseClass.actualClass != UserResponse::class.java) return body
                val data = gson.fromJson(body, Array<UserResponse>::class.java).toList()
                logd("Applying query parameters for $requestUrl...")
                applyQueryParams(data, queryParams)
            }
        }
    }

    private fun createErrorResponse(
        request: Request,
        code: Int = StatusCode.INTERNAL_SERVER_ERROR.code,
        errorBody: String = "Undefined error"
    ): Response {
        return Response.Builder()
            .code(code)
            .request(request)
            .protocol(Protocol.HTTP_2)
            .message(errorBody)
            .body(
                errorBody.toResponseBody("text/plain".toMediaType())
            )
            .build()
    }

    fun mock(
        base: String = BuildConfig.BASE_URL,
        path: String,
        body: String,
        status: Int,
        delayInMs: Long = 500,
        persist: Boolean = true,
        errorFrequencyInPercent: Int = 0,
        responseClass: MockNetworkResponseClass? = null,
    ) = apply {
        val mockResponse =
            MockResponse(
                "$base$path",
                body,
                status,
                delayInMs,
                persist,
                errorFrequencyInPercent,
                responseClass,
            )
        mockResponses.add(mockResponse)
    }

    private fun findParameterValue(src: String, parameterName: String): String? {
        val requestQuery = if (src.contains('?')) {
            src.substring(src.indexOf('?') + 1)
        } else return null
        return requestQuery.split('&').map {
            val parts = it.split('=')
            val name = parts.firstOrNull() ?: ""
            val value = parts.drop(1).firstOrNull() ?: ""
            Pair(name, value)
        }.firstOrNull { it.first == parameterName }?.second
    }
}

data class MockQueryParams(
    val offset: Int? = null,
    val amount: Int? = null,
    val query: String? = null,
) {
    fun isNotNull() = offset != null || amount != null || query != null
}
