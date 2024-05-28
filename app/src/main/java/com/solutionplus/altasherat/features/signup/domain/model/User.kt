package com.solutionplus.altasherat.features.signup.domain.model

data class User(
    val id: Int? = null,
    val userName: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val firstName: String? = null,
    val middleName: String? = null,
    val lastName: String? = null,
    val phone: String? = null,
    val birthDate: String? = null,
    val imageUrl: String? = null,
    val emailVerified: Boolean?= null,

    )
