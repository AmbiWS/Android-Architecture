package com.ambiws.androidarchitecture.features.lists.list.ui

import androidx.lifecycle.MutableLiveData
import com.ambiws.androidarchitecture.base.BaseViewModel
import com.ambiws.androidarchitecture.features.lists.list.ui.list.UserDefaultItemModel
import com.ambiws.androidarchitecture.features.lists.list.ui.list.UserListItemModel
import com.ambiws.androidarchitecture.features.lists.list.ui.list.UserPremiumItemModel
import com.ambiws.androidarchitecture.features.lists.user.domain.interactor.UserInteractor
import com.ambiws.androidarchitecture.features.lists.user.domain.model.User
import com.ambiws.androidarchitecture.features.lists.user.domain.model.toDefaultUserItemModel
import com.ambiws.androidarchitecture.features.lists.user.domain.model.toPremiumUserItemModel
import com.ambiws.androidarchitecture.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
            rearrangeAndUpdateUsers(currentList)
            usersListExtendedLiveEvent.call()
        }
    }

    fun clearUsersList() {
        usersLiveData.value = emptyList()
    }

    fun initUsersList(applyLoader: Boolean = false) {
        launch(showDefaultLoader = applyLoader) {
            val usersList = userInteractor.getUsersList()
            usersLiveData.value = parseUsersData(usersList)
            usersListLoadedLiveEvent.call()
        }
    }

    fun setFavorite(id: Long, isFavorite: Boolean) {
        launch {
            val currentList = usersLiveData.value?.map {
                if (it is UserDefaultItemModel && it.id == id) {
                    it.copy(isFavorite = isFavorite)
                } else {
                    it
                }
            } ?: return@launch
            rearrangeAndUpdateUsers(currentList)
        }
    }

    // Sort users by premium and favorite factors
    private fun rearrangeAndUpdateUsers(list: List<UserListItemModel>) {
        launch {
            val updatedUsersList = mutableListOf<UserListItemModel>()
            withContext(Dispatchers.Default) {
                val premiumUsers = list.filterIsInstance<UserPremiumItemModel>()
                val defaultUsers = list.filterIsInstance<UserDefaultItemModel>().sortedByDescending { it.isFavorite == true }
                updatedUsersList.addAll(premiumUsers)
                updatedUsersList.addAll(defaultUsers)
            }
            usersLiveData.value = updatedUsersList
        }
    }

    // Separate users by type and merge into items list
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
