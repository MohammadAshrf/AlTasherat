package com.solutionplus.altasherat.features.login.domain.repository

import com.solutionplus.altasherat.features.login.domain.model.Login
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest

interface ILoginRepository {
    suspend fun loginWithPhone(loginRequest: LoginRequest): Login
    suspend fun saveAccessToken(token: String)
}