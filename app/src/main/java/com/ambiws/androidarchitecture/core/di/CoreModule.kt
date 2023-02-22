package com.ambiws.androidarchitecture.core.di

import com.ambiws.androidarchitecture.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {
    viewModel {
        MainViewModel()
    }
}
