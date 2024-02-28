package com.ambiws.androidarchitecture.features.lists.user.di

import com.ambiws.androidarchitecture.features.lists.user.data.dataSource.UserDataSource
import com.ambiws.androidarchitecture.features.lists.user.data.dataSource.UserDataSourceImpl
import com.ambiws.androidarchitecture.features.lists.user.domain.interactor.UserInteractor
import com.ambiws.androidarchitecture.features.lists.user.domain.interactor.UserInteractorImpl
import org.koin.dsl.module

val userModule = module {

    single<UserDataSource> {
        UserDataSourceImpl(userApi = get())
    }

    factory<UserInteractor> {
        UserInteractorImpl(userDataSource = get())
    }
}
