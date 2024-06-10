package com.solutionplus.altasherat.features.personalInfo.domain.repository

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateProfileInfo
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface IUpdateProfileRepository {
    suspend fun updateProfileInfo(remoteRequest: RemoteRequest): UpdateProfileInfo
    suspend fun getProfileInfoRemote(): User
}