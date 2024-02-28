package com.ambiws.androidarchitecture.features.lists.list.ui.list

data class UserDefaultItemModel(
    val id: Long,
    val nameAndAge: String?,
    val gender: String?,
    val company: String?,
    val address: String?,
    val skills: String?,
    val isFavorite: Boolean? = false,
) : UserListItemModel
