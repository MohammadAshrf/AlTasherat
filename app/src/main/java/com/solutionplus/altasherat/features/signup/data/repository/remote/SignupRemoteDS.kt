package com.solutionplus.altasherat.features.signup.data.repository.remote

import com.google.gson.reflect.TypeToken
import com.solutionplus.altasherat.common.data.constants.Constants.NO_AUTHENTICATION
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.signup.data.model.dto.SignupDto
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS

internal class SignupRemoteDS(private val provider: INetworkProvider) : ISignupRemoteDS {

    override suspend fun signupWithPhone(
        signupRequest: SignupRequest
    ): SignupDto? {
        return provider.post(
            responseWrappedModel = SignupDto::class.java, pathUrl = "signup",
            headers = NO_AUTHENTICATION , requestBody = signupRequest
        )
    }

}