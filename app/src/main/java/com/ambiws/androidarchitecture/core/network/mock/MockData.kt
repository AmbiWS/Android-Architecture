package com.ambiws.androidarchitecture.core.network.mock

import com.ambiws.androidarchitecture.core.network.adapters.model.StatusCode
import com.ambiws.androidarchitecture.features.user.data.response.UserResponse

object MockData {
    val errorBadRequest = MockErrorResponse(
        status = StatusCode.BAD_REQUEST.code,
        message = "Bad request",
        errors = null,
    )

    val errorUnauthorized = MockErrorResponse(
        status = StatusCode.UNAUTHORIZED.code,
        message = "Unauthorized",
        errors = null,
    )

    val errorServerUnexpected = MockErrorResponse(
        status = StatusCode.INTERNAL_SERVER_ERROR.code,
        message = null,
        errors = null,
    )

    val users = listOf(
        UserResponse(
            id = 2089L,
            name = "Sarah Bright",
            age = 23,
            gender = "Female",
            company = null,
            skills = listOf(
                "Kotlin",
                "Architecture",
                "CI/CD",
            ),
            address = null,
        ),
        UserResponse(
            id = 2177L,
            name = "Nick Fatale",
            age = 44,
            gender = "Male",
            company = "Google",
            skills = listOf(
                "Data structures",
                "Algorithms",
            ),
            address = "4455 Landing Lange, APT 4, Louisville, KY 40018-1234",
        ),
        UserResponse(
            id = 3001L,
            name = "Suzy Nolan",
            age = 40,
            gender = null,
            company = null,
            skills = null,
            address = null,
        ),
    )

    val usersId = users.map {
        it.id
    }

    fun getUserById(id: Long): UserResponse? {
        return users.firstOrNull { it.id == id }
    }
}
