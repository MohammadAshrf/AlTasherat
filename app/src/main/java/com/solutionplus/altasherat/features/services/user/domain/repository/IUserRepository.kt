package com.solutionplus.altasherat.features.services.user.domain.repository

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface IUserRepository {
    suspend fun saveUser(user: User)
    suspend fun getUser(): UserEntity
}