package com.ambiws.androidarchitecture.core.di

import com.ambiws.androidarchitecture.utils.providers.PreferencesProvider
import com.ambiws.androidarchitecture.utils.providers.PreferencesProviderImpl
import com.ambiws.androidarchitecture.utils.providers.ResourceProvider
import com.ambiws.androidarchitecture.utils.providers.ResourceProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {
    factory<PreferencesProvider> { PreferencesProviderImpl(androidContext()) }
    factory<ResourceProvider> { ResourceProviderImpl(androidContext()) }
}
