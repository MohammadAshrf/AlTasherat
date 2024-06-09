package com.solutionplus.altasherat.features.signup.domain.repository.remote

import com.solutionplus.altasherat.features.signup.data.model.dto.SignupDto
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest

internal interface ISignupRemoteDS {
    suspend fun signupWithPhone(signupRequest: SignupRequest): SignupDto?
}