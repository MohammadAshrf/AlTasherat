package com.solutionplus.altasherat.features.personalInfo.domain.repository.remote

import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UpdateUserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserRequest

internal interface IUpdateUserRemoteDS {
    suspend fun updateUser(updateUserRequest: UpdateUserRequest): UpdateUserDto
}