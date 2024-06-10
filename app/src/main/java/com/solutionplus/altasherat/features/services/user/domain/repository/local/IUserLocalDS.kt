package com.solutionplus.altasherat.features.services.user.domain.repository.local

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity

internal interface IUserLocalDS {
    suspend fun saveUser(user: UserEntity)
    suspend fun getUserLocal(): UserEntity
}