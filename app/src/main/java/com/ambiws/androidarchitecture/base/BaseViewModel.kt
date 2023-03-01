package com.ambiws.androidarchitecture.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ambiws.androidarchitecture.base.navigation.NavigationCommand
import com.ambiws.androidarchitecture.base.navigation.ViewModelNavigation

abstract class BaseViewModel : ViewModel() {

    protected val navigation by lazy { ViewModelNavigation() }
    val navigationCommand: LiveData<NavigationCommand> by lazy { navigation.navigationCommand }

    fun navigateBack(hideKeyboard: Boolean = true) = navigation.navigateBack(hideKeyboard)
}
