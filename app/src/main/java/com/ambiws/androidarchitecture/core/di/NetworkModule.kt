package com.ambiws.androidarchitecture.core.di

import com.ambiws.androidarchitecture.BuildConfig
import com.ambiws.androidarchitecture.core.network.adapters.ErrorCallAdapterFactory
import com.ambiws.androidarchitecture.core.network.adapters.ExceptionParser
import com.ambiws.androidarchitecture.core.network.api.UserApi
import com.ambiws.androidarchitecture.core.network.mock.MockInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    val isDebugBuild = BuildConfig.DEBUG

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        GsonBuilder().setLenient().serializeNulls().create()
    }

    single {
        GsonConverterFactory.create(get())
    }

    single {
        ErrorCallAdapterFactory()
    }

    single {
        ExceptionParser(get())
    }

    single {
        if (isDebugBuild) {
            OkHttpClient.Builder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .addInterceptor(get<MockInterceptor>())
                .build()
        } else {
            OkHttpClient.Builder().build()
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<ErrorCallAdapterFactory>())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(UserApi::class.java)
    }
}
