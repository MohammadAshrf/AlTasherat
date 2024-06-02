package com.solutionplus.altasherat.features.personalInfo.data.repository.remote

import com.solutionplus.altasherat.common.data.constants.Constants.NO_AUTHENTICATION
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UpdateUserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserRequest
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateUserRemoteDS

internal class UpdateUserRemoteDS(private val networkProvider: INetworkProvider) :
    IUpdateUserRemoteDS {
    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): UpdateUserDto {
        return networkProvider.post(
            responseWrappedModel = UpdateUserDto::class.java, pathUrl = "update-account",
            requestBody = updateUserRequest
        )
    }

    override suspend fun getUpdateUser(): UserDto {
        return networkProvider.get(
            responseWrappedModel = UserDto::class.java, pathUrl = "show-account"
        )
    }
}