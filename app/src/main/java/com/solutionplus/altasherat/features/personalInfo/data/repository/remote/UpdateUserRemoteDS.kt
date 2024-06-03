package com.solutionplus.altasherat.features.personalInfo.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.AccountResponseDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UpdateUserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateUserRemoteDS

internal class UpdateUserRemoteDS(private val networkProvider: INetworkProvider) :
    IUpdateUserRemoteDS {
    override suspend fun updateUser(updateUserRequest: UpdateUserInfoRequest): UpdateUserDto {
        return networkProvider.post(
            responseWrappedModel = UpdateUserDto::class.java, pathUrl = "update-account",
            requestBody = updateUserRequest
        )
    }

    override suspend fun getUpdateUser(): AccountResponseDto {
        return networkProvider.get(
            responseWrappedModel = AccountResponseDto::class.java, pathUrl = "show-account"
        )
    }
}