package com.solutionplus.altasherat.features.changepassword.domain.repository.remote

import com.solutionplus.altasherat.features.changepassword.data.model.dto.ChangePasswordDto
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest

internal interface IChangePasswordRemoteDS {
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ChangePasswordDto?
}