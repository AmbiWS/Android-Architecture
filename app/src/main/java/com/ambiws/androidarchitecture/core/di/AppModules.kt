package com.ambiws.androidarchitecture.core.di

import com.ambiws.androidarchitecture.core.network.mock.mockNetworkModule
import com.ambiws.androidarchitecture.features.home.di.homeModule
import com.ambiws.androidarchitecture.features.lists.list.di.listModule
import com.ambiws.androidarchitecture.features.lists.user.di.userModule

object AppModules {
    val applicationModules = listOf(
        coreModule,
        utilsModule,
        networkModule,
        mockNetworkModule,
        homeModule,
        userModule,
        listModule
    )
}
