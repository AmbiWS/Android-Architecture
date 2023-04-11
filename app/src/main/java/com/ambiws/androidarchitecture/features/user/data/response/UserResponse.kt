package com.ambiws.androidarchitecture.features.user.data.response

import com.ambiws.androidarchitecture.features.user.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String?,
    @SerializedName("age")
    val age: Short?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("skills")
    val skills: List<String>?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("isPremium")
    val isPremium: Boolean?,
)

fun UserResponse.toDomain() = User(
    id = id,
    name = name.orEmpty(),
    age = age ?: 0,
    gender = gender,
    company = company,
    skills = skills,
    address = address,
    bio = bio,
    isPremium = isPremium ?: false,
)
