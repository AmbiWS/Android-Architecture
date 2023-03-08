package com.ambiws.androidarchitecture.core.network.api

import com.ambiws.androidarchitecture.features.user.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

const val PATH_USER_BY_ID = "{userId}"
const val API_USERS_LIST = "usersList"
const val API_USER_BY_ID = "$API_USERS_LIST/$PATH_USER_BY_ID"

interface UserApi {

    @GET(API_USERS_LIST)
    suspend fun getUsers(): List<UserResponse>

    @GET(API_USER_BY_ID)
    suspend fun getUserById(@Path("userId") userId: Long): UserResponse
}
