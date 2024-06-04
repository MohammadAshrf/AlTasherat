package com.solutionplus.altasherat.features.personalInfo.domain.repository

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.models.User

interface IUpdateUserRepository {
    suspend fun updateUserInfo(remoteRequest: RemoteRequest): UpdateUser
    suspend fun saveUpdatedUser(updatedUser: User)
    suspend fun getUpdatedUserFromLocal(): User
    suspend fun getUpdatedUserFromRemote(): User
}