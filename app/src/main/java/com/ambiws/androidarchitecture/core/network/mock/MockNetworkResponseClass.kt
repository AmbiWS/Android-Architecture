package com.ambiws.androidarchitecture.core.network.mock

import com.ambiws.androidarchitecture.features.lists.user.data.response.UserResponse

enum class MockNetworkResponseClass(val actualClass: Class<*>) {
    USER(UserResponse::class.java),
}
