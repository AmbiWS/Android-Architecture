package com.ambiws.androidarchitecture.core.network.utils

enum class StatusCode(val code: Int) {
    SUCCESS(200),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
}
