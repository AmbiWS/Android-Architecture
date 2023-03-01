package com.ambiws.androidarchitecture.core.di

import com.ambiws.androidarchitecture.features.home.di.homeModule

object AppModules {
    val applicationModules = listOf(
        coreModule,
        homeModule,
    )
}
