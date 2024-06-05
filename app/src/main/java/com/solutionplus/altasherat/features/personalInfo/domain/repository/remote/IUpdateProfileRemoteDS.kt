package com.solutionplus.altasherat.features.personalInfo.domain.repository.remote

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ProfileInfoDto

internal interface IUpdateProfileRemoteDS {
    suspend fun updateProfileInfo(remoteRequest: RemoteRequest): ProfileInfoDto
    suspend fun getProfileInfo(): ProfileInfoDto
}