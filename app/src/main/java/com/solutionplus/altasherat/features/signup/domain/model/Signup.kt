package com.solutionplus.altasherat.features.signup.domain.model

import com.solutionplus.altasherat.features.services.user.domain.models.User


data class Signup(
    val message: String,
    val token: String,
    val user: User
)