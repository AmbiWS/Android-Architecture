package com.ambiws.androidarchitecture.features.list.ui

import com.ambiws.androidarchitecture.base.BaseViewModel
import com.ambiws.androidarchitecture.features.user.domain.interactor.UserInteractor
import com.ambiws.androidarchitecture.utils.logd

class ListViewModel(
    private val userInteractor: UserInteractor
) : BaseViewModel() {

    init {
        launch {
            val usersList = userInteractor.getUsersList()
            logd("Users list: $usersList")
        }
    }
}
