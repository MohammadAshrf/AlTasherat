package com.solutionplus.altasherat.features.profileMenu.domain.model

data class User(
    val id: Int,
    val userName: String,
    val fullName: String,
    val email: String,
    val password: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val phone: String,
    val birthDate: String,
    val emailVerified: Boolean,
    val imageUrl: String,
    )
