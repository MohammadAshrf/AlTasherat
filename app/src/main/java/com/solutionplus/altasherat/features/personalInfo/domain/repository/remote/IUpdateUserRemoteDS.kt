package com.solutionplus.altasherat.features.personalInfo.domain.repository.remote

import com.solutionplus.altasherat.features.personalInfo.data.models.dto.AccountResponseDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UpdateUserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.User

internal interface IUpdateUserRemoteDS {
    suspend fun updateUser(updateUserRequest: UpdateUserRequest): UpdateUserDto
    suspend fun getUpdateUser(): AccountResponseDto
}