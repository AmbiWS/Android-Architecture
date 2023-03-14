package com.ambiws.androidarchitecture.core.network.adapters.model.exceptions

open class ServerError(
    override val message: String,
    val code: Int,
    cause: Throwable? = null,
    val errorsList: List<String>? = null,
    val headers: Map<String, String>? = null,
) : Exception(message, cause)
