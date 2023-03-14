package com.ambiws.androidarchitecture.features.user.data.dataSource

import com.ambiws.androidarchitecture.core.network.api.UserApi
import com.ambiws.androidarchitecture.features.user.data.response.UserResponse

interface UserDataSource {
    suspend fun getUsersList(): List<UserResponse>
    suspend fun getUserById(id: Long): UserResponse
}

class UserDataSourceImpl(private val userApi: UserApi) : UserDataSource {

    override suspend fun getUsersList(): List<UserResponse> {
        return userApi.getUsers()
    }

    override suspend fun getUserById(id: Long): UserResponse {
        return userApi.getUserById(userId = id)
    }
}
