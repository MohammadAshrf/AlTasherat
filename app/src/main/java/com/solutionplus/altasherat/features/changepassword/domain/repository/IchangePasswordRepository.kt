package com.solutionplus.altasherat.features.changepassword.domain.repository

import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest

 interface IchangePasswordRepository {
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ChangePasswordRequest?
    suspend fun getAccessToKen(): String?
}