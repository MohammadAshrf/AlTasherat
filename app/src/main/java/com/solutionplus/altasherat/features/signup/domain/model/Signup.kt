package com.solutionplus.altasherat.features.signup.domain.model



data class Signup(
    val message: String,
    val token: String,
    val user: User
)