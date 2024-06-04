package com.solutionplus.altasherat.features.personalInfo.domain.repository.remote

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.AccountResponseDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UpdateUserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest

internal interface IUpdateUserRemoteDS {
    suspend fun updateUser(remoteRequest: RemoteRequest): UpdateUserDto
    suspend fun getUpdateUser(): AccountResponseDto
}