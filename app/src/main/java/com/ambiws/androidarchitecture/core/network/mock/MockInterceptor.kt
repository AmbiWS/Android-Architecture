package com.ambiws.androidarchitecture.core.network.mock

import com.ambiws.androidarchitecture.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import kotlin.random.Random

class MockInterceptor : Interceptor {

    private val mockResponses = mutableListOf<MockResponse>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val mockResponse = findMockResponseInList(request)
            ?: throw RuntimeException("No mock response found for url ${request.url}.")

        removeIfNotPersisted(mockResponse)
        applyNetworkDelay(mockResponse)

        return if (mockResponse.status < 400) {
            if (mockResponse.errorFrequencyInPercent <= 0) {
                createSuccessResponse(mockResponse, request)
            } else {
                createUnknownResponse(mockResponse, request)
            }
        } else {
            createErrorResponse(request, errorBody = mockResponse.body())
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
        if (mockResponse.errorFrequencyInPercent > 100) {
            throw IllegalArgumentException("Error frequency in percent cannot exceed 100.")
        }
        return when (Random.nextInt(0, 101)) {
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
        code: Int = 500,
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
