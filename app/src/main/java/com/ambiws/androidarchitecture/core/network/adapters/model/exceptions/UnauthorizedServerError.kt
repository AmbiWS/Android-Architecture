package com.ambiws.androidarchitecture.core.network.adapters.model.exceptions

import com.ambiws.androidarchitecture.core.network.adapters.model.StatusCode

class UnauthorizedServerError(
    override val message: String,
    cause: Throwable,
) : ServerError(
    message = message,
    code = StatusCode.UNAUTHORIZED.code,
    cause = cause,
)
