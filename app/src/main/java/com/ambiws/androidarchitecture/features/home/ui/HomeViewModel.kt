package com.ambiws.androidarchitecture.features.home.ui

import com.ambiws.androidarchitecture.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    fun navigateToListFragment() {
        navigation.navigate(
            HomeFragmentDirections.actionHomeFragmentToListFragment()
        )
    }
}
