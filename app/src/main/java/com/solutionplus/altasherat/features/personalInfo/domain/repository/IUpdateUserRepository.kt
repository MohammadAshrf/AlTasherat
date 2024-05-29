package com.solutionplus.altasherat.features.personalInfo.domain.repository

import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser

interface IUpdateUserRepository {
    suspend fun updateUser(updateUserRequest: UpdateUserRequest) : UpdateUser
    suspend fun saveUpdatedUser(updatedUser: UpdateUser)
    suspend fun getUpdatedUserFromLocal() : UpdateUser
}