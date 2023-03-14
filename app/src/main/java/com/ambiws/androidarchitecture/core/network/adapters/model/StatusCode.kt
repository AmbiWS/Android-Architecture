package com.ambiws.androidarchitecture.core.network.adapters.model

import java.net.HttpURLConnection.*

enum class StatusCode(val code: Int) {
    SUCCESS(HTTP_OK),
    BAD_REQUEST(HTTP_BAD_REQUEST),
    UNAUTHORIZED(HTTP_UNAUTHORIZED),
    NOT_FOUND(HTTP_NOT_FOUND),
    CLIENT_TIMEOUT(HTTP_CLIENT_TIMEOUT),
    VALIDATION(422),
    INTERNAL_SERVER_ERROR(HTTP_INTERNAL_ERROR),
    UNAVAILABLE(HTTP_UNAVAILABLE)
}
