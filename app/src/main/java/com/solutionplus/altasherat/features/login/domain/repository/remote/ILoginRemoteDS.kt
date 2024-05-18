package com.solutionplus.altasherat.features.login.domain.repository.remote

import com.solutionplus.altasherat.features.login.data.model.dto.LoginDto
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest

internal interface ILoginRemoteDS {
    suspend fun loginWithPhone(loginRequest: LoginRequest): LoginDto?
}