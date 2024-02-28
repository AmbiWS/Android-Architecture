package com.ambiws.androidarchitecture.features.lists.user.data.dataSource

import com.ambiws.androidarchitecture.core.network.api.UserApi
import com.ambiws.androidarchitecture.features.lists.user.data.response.UserResponse

interface UserDataSource {
    suspend fun getUsersList(offset: Int, amount: Int? = null): List<UserResponse>
    suspend fun getUserById(id: Long): UserResponse
}

class UserDataSourceImpl(private val userApi: UserApi) : UserDataSource {

    override suspend fun getUserById(id: Long): UserResponse {
        return userApi.getUserById(userId = id)
    }

    override suspend fun getUsersList(offset: Int, amount: Int?): List<UserResponse> {
        return if (amount != null) {
            userApi.getUsers(offset, amount)
        } else {
            userApi.getUsers(offset)
        }
    }
}
