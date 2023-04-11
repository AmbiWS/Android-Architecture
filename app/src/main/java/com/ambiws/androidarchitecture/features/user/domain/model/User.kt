package com.ambiws.androidarchitecture.features.user.domain.model

import com.ambiws.androidarchitecture.features.list.ui.list.UserDefaultItemModel
import com.ambiws.androidarchitecture.features.list.ui.list.UserPremiumItemModel

data class User(
    val id: Long,
    val name: String,
    val age: Short,
    val gender: String?,
    val company: String?,
    val skills: List<String>?,
    val address: String?,
    val bio: String?,
    val isPremium: Boolean,
) {
    val nameAndAge: String
        get() = "$name, $age"
}

fun User.toDefaultUserItemModel() =
    UserDefaultItemModel(
        id = id,
        nameAndAge = nameAndAge,
        gender = gender,
        company = company,
        address = address,
        skills = skills?.joinToString(separator = "\n")
    )

fun User.toPremiumUserItemModel() =
    UserPremiumItemModel(
        id = id,
        nameAndAge = nameAndAge,
        gender = gender,
        bio = bio,
    )
