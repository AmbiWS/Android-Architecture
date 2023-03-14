package com.ambiws.androidarchitecture.core.network.adapters.model.exceptions

import com.ambiws.androidarchitecture.core.network.adapters.model.StatusCode

class ValidationServerError(
    override val message: String,
    cause: Throwable,
    errorsList: List<String>? = null,
) : ServerError(
    message = message,
    code = StatusCode.VALIDATION.code,
    cause = cause,
    errorsList = errorsList,
)
