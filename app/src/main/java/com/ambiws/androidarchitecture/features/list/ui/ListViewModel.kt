package com.ambiws.androidarchitecture.features.list.ui

import androidx.lifecycle.viewModelScope
import com.ambiws.androidarchitecture.base.BaseViewModel
import com.ambiws.androidarchitecture.features.user.domain.interactor.UserInteractor
import com.ambiws.androidarchitecture.utils.logd
import kotlinx.coroutines.launch

class ListViewModel(
    private val userInteractor: UserInteractor
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            val usersList = userInteractor.getUsersList()
            logd("Users list: $usersList")
            val user = userInteractor.getUserById(2177L)
            logd("User #2177: $user")
        }
    }
}
