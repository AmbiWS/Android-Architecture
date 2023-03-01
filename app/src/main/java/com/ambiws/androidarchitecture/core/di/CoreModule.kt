package com.ambiws.androidarchitecture.core.di

import com.ambiws.androidarchitecture.MainViewModel
import com.ambiws.androidarchitecture.base.EmptyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {
    viewModel {
        MainViewModel()
    }
    viewModel {
        EmptyViewModel()
    }
}
