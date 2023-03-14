package com.ambiws.androidarchitecture.features.user.data.response

import com.ambiws.androidarchitecture.features.user.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("age")
    val age: Short,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("skills")
    val skills: List<String>?,
    @SerializedName("address")
    val address: String?
)

fun UserResponse.toDomain() = User(
    id = id,
    name = name,
    age = age,
    gender = gender,
    company = company,
    skills = skills,
    address = address
)
