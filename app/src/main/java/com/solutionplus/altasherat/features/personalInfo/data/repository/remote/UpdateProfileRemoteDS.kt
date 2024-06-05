package com.solutionplus.altasherat.features.personalInfo.data.repository.remote

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ProfileInfoDto
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateProfileRemoteDS

internal class UpdateProfileRemoteDS(private val provider: INetworkProvider) :
    IUpdateProfileRemoteDS {
    override suspend fun updateProfileInfo(remoteRequest: RemoteRequest): ProfileInfoDto {
        return provider.postWithFiles(
            responseWrappedModel = ProfileInfoDto::class.java, pathUrl = "update-account",
            requestBody = remoteRequest.requestBody, filesMap = remoteRequest.requestBodyFiles
        )
    }

    override suspend fun getProfileInfo(): ProfileInfoDto {
        return provider.get(
            responseWrappedModel = ProfileInfoDto::class.java, pathUrl = "show-account"
        )
    }
}