package com.ambiws.androidarchitecture.features.list.di

import com.ambiws.androidarchitecture.features.list.ui.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listModule = module {

    viewModel {
        ListViewModel(userInteractor = get())
    }
}
