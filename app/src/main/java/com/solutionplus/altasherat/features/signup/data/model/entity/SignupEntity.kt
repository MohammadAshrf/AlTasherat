package com.solutionplus.altasherat.features.signup.data.model.entity

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity

internal data class SignupEntity(
    val token: String,
    val user: UserEntity
)