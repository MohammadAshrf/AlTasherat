package com.solutionplus.altasherat.features.services.user.domain.repository

import com.solutionplus.altasherat.features.services.user.domain.models.User

interface IUserRepository {
    suspend fun saveUser(user: User)
    suspend fun getUserLocal(): User
}