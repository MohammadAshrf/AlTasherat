package com.solutionplus.altasherat.features.login.data.model.entity

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity

internal data class LoginEntity (
    val token: String,
    val user: UserEntity
)