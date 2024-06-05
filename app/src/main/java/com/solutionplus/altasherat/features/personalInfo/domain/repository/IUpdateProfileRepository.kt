package com.solutionplus.altasherat.features.personalInfo.domain.repository

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.models.User

interface IUpdateProfileRepository {
    suspend fun updateProfileInfo(remoteRequest: RemoteRequest): UpdateUser
    suspend fun saveProfileInfo(updatedUser: User)
    suspend fun getProfileInfoLocal(): User
    suspend fun getProfileInfoRemote(): User
}