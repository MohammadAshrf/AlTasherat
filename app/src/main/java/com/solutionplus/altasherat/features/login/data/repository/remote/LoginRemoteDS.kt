package com.solutionplus.altasherat.features.login.data.repository.remote

import com.solutionplus.altasherat.common.data.constants.Constants.NO_AUTHENTICATION
import com.solutionplus.altasherat.features.login.data.model.dto.LoginDto
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider

internal class LoginRemoteDS(private val provider: INetworkProvider) : ILoginRemoteDS {

    override suspend fun loginWithPhone(
        loginRequest: LoginRequest
    ): LoginDto {
        return provider.post(
            responseWrappedModel = LoginDto::class.java, pathUrl = "login",
            headers = NO_AUTHENTICATION, requestBody = loginRequest
        )
    }

}