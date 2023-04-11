package com.ambiws.androidarchitecture.features.list.ui

import androidx.lifecycle.MutableLiveData
import com.ambiws.androidarchitecture.base.BaseViewModel
import com.ambiws.androidarchitecture.features.list.ui.list.UserDefaultItemModel
import com.ambiws.androidarchitecture.features.list.ui.list.UserListItemModel
import com.ambiws.androidarchitecture.features.list.ui.list.UserPremiumItemModel
import com.ambiws.androidarchitecture.features.user.domain.interactor.UserInteractor
import com.ambiws.androidarchitecture.features.user.domain.model.User
import com.ambiws.androidarchitecture.features.user.domain.model.toDefaultUserItemModel
import com.ambiws.androidarchitecture.features.user.domain.model.toPremiumUserItemModel
import com.ambiws.androidarchitecture.utils.SingleLiveEvent

class ListViewModel(
    private val userInteractor: UserInteractor
) : BaseViewModel() {

    val usersLiveData = MutableLiveData<List<UserListItemModel>>()
    val usersListLoadedLiveEvent = SingleLiveEvent<Unit>()
    val usersListExtendedLiveEvent = SingleLiveEvent<Unit>()

    init {
        initUsersList(applyLoader = true)
    }

    fun loadMoreUsers() {
        launch(showDefaultLoader = true) {
            val currentList = usersLiveData.value as MutableList
            currentList.addAll(
                parseUsersData(
                    userInteractor.getUsersList(offset = currentList.size)
                )
            )
            usersLiveData.value = currentList
            usersListExtendedLiveEvent.call()
        }
    }

    fun clearUsersList() {
        usersLiveData.value = emptyList()
    }

    // TODO implement favorite users feature (room or alt)
    fun initUsersList(applyLoader: Boolean = false) {
        launch(showDefaultLoader = applyLoader) {
            val usersList = userInteractor.getUsersList()
            usersLiveData.value = parseUsersData(usersList)
            usersListLoadedLiveEvent.call()
        }
    }

    private fun parseUsersData(data: List<User>): List<UserListItemModel> {
        val usersDefaultList = mutableListOf<UserDefaultItemModel>()
        val usersPremiumList = mutableListOf<UserPremiumItemModel>()
        data.forEach {
            if (it.isPremium) {
                usersPremiumList.add(it.toPremiumUserItemModel())
            } else {
                usersDefaultList.add(it.toDefaultUserItemModel())
            }
        }
        val usersList = mutableListOf<UserListItemModel>()
        usersList.addAll(usersPremiumList)
        usersList.addAll(usersDefaultList)
        return usersList
    }
}
