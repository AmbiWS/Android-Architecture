package com.ambiws.androidarchitecture.core.network.mock

data class MockResponse(
    val path: String,
    val body: () -> String,
    val status: Int,
    val delayInMs: Long,
    val persist: Boolean,
    val errorFrequencyInPercent: Int
)
