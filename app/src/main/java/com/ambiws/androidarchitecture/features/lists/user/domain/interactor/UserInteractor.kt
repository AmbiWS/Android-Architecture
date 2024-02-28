package com.ambiws.androidarchitecture.features.lists.user.domain.interactor

import com.ambiws.androidarchitecture.features.lists.user.data.dataSource.UserDataSource
import com.ambiws.androidarchitecture.features.lists.user.data.response.toDomain
import com.ambiws.androidarchitecture.features.lists.user.domain.model.User

interface UserInteractor {
    suspend fun getUsersList(offset: Int = 0, amount: Int? = null): List<User>
    suspend fun getUserById(id: Long): User
}

class UserInteractorImpl(private val userDataSource: UserDataSource) : UserInteractor {

    override suspend fun getUsersList(offset: Int, amount: Int?): List<User> {
        return userDataSource.getUsersList(
            offset,
            amount,
        ).map {
            it.toDomain()
        }
    }

    override suspend fun getUserById(id: Long): User {
        return userDataSource.getUserById(id = id).toDomain()
    }
}
