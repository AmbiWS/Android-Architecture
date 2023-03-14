package com.ambiws.androidarchitecture.core.network.mock

data class MockErrorResponse(
    val status: Int?,
    val message: String?,
    val errors: List<String>?,
)
