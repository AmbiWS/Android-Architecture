package com.ambiws.androidarchitecture.features.user.domain.model

data class User(
    val id: Long,
    val name: String,
    val age: Short,
    val gender: String?,
    val company: String?,
    val skills: List<String>?,
    val address: String?,
)
