package com.solutionplus.altasherat.features.login.domain.model

import com.solutionplus.altasherat.features.login.domain.model.User


data class Login(
    val message :String,
    val accessToken: String,
    val userInfo: User,
)
