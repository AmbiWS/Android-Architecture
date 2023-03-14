package com.ambiws.androidarchitecture.core.network.mock

import com.ambiws.androidarchitecture.core.network.adapters.model.StatusCode
import com.ambiws.androidarchitecture.core.network.api.*
import com.google.gson.Gson
import org.koin.dsl.module

val mockNetworkModule = module {

    single {
        MockInterceptor()
            // Mock response for [http://localhost/usersList]
            .mock(
                path = API_USERS_LIST,
                body = { Gson().toJson(MockData.users) },
                status = StatusCode.SUCCESS.code,
                delayInMs = 900L
            )
            // Mock response for [http://localhost/usersList/{userId}]
            .mockUsersById(MockData.usersId.toMutableList())
    }
}

private fun MockInterceptor.mockUsersById(values: MutableList<Long>): MockInterceptor {
    return if (values.isEmpty()) {
        this
    } else {
        val userId = values.removeAt(0)
        this.mock(
            path = API_USER_BY_ID.replacePath(PATH_USER_BY_ID, userId.toString()),
            body = { Gson().toJson(MockData.getUserById(userId)) },
            status = StatusCode.SUCCESS.code,
            delayInMs = 500L
        ).mockUsersById(values)
    }
}

private fun String.replacePath(path: String, value: String): String {
    val pathRegex = Regex(
        path.replace("{", "\\{")
            .replace("}", "\\}")
    )
    return this.replaceFirst(pathRegex, value)
}
