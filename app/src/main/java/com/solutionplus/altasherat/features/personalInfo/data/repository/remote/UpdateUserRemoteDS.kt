package com.solutionplus.altasherat.features.personalInfo.data.repository.remote

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.AccountResponseDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UpdateUserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateUserRemoteDS

internal class UpdateUserRemoteDS(private val provider: INetworkProvider) :
    IUpdateUserRemoteDS {
    override suspend fun updateUser(remoteRequest: RemoteRequest): UpdateUserDto {
        return provider.postWithFiles(
            responseWrappedModel = UpdateUserDto::class.java, pathUrl = "update-account",
            requestBody = remoteRequest.requestBody, filesMap = remoteRequest.requestBodyFiles
        )
    }

    override suspend fun getUpdateUser(): AccountResponseDto {
        return provider.get(
            responseWrappedModel = AccountResponseDto::class.java, pathUrl = "show-account"
        )
    }
}