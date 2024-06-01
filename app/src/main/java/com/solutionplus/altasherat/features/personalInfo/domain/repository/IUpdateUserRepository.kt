package com.solutionplus.altasherat.features.personalInfo.domain.repository

import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.models.User

interface IUpdateUserRepository {
    suspend fun updateUser(updateUserRequest: UpdateUserRequest): UpdateUser
    suspend fun saveUpdatedUser(updatedUser: UpdateUser)
    suspend fun getUpdatedUserFromLocal(): User
    suspend fun getUpdatedUserFromRemote(): User
    suspend fun hasUser(): Boolean
}