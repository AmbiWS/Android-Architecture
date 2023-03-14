package com.ambiws.androidarchitecture.core.network.mock

import com.ambiws.androidarchitecture.BuildConfig
import com.ambiws.androidarchitecture.core.network.adapters.model.StatusCode
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
            createErrorResponse(request, code = mockResponse.status, errorBody = mockResponse.body())
        }
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

    private fun createSuccessResponse(
        mockResponse: MockResponse,
        request: Request
    ): Response {
        return Response.Builder()
            .code(mockResponse.status)
            .request(request)
            .protocol(Protocol.HTTP_2)
            .message("OK")
            .body(
                mockResponse.body.invoke().toResponseBody("application/json".toMediaType())
            )
            .build()
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
        body: () -> String,
        status: Int,
        delayInMs: Long = 500,
        persist: Boolean = true,
        errorFrequencyInPercent: Int = 0
    ) = apply {
        val mockResponse =
            MockResponse(
                "$base$path",
                body,
                status,
                delayInMs,
                persist,
                errorFrequencyInPercent
            )
        mockResponses.add(mockResponse)
    }
}
