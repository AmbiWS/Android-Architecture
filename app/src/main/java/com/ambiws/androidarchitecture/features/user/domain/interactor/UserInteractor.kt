package com.ambiws.androidarchitecture.features.user.domain.interactor

import com.ambiws.androidarchitecture.features.user.data.dataSource.UserDataSource
import com.ambiws.androidarchitecture.features.user.data.response.toDomain
import com.ambiws.androidarchitecture.features.user.domain.model.User

interface UserInteractor {
    suspend fun getUsersList(): List<User>
    suspend fun getUserById(id: Long): User
}

class UserInteractorImpl(private val userDataSource: UserDataSource) : UserInteractor {

    override suspend fun getUsersList(): List<User> {
        return userDataSource.getUsersList().map {
            it.toDomain()
        }
    }

    override suspend fun getUserById(id: Long): User {
        return userDataSource.getUserById(id = id).toDomain()
    }
}
