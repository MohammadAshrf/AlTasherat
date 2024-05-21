package com.solutionplus.altasherat.features.profileMenu.data.model.entity

data class UserEntity(
    val id: Int,
    val userName: String,
    val firsName: String,
    val middleName: String,
    val lastName: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val birthDate: String,
    val imageUrl: String,
)
