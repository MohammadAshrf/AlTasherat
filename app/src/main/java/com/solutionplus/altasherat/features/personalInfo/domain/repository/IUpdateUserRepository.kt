package com.solutionplus.altasherat.features.personalInfo.domain.repository

import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface IUpdateUserRepository {
    suspend fun updateUserInfo(updateUserRequest: UpdateUserInfoRequest): UpdateUser
    suspend fun getUpdatedUserFromRemote(): User
}