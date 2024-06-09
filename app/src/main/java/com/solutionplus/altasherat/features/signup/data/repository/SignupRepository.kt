package com.solutionplus.altasherat.features.signup.data.repository


import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.data.mapper.SignupMapper
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import com.solutionplus.altasherat.features.signup.domain.repository.local.ISignupLocalDS
import com.solutionplus.altasherat.features.signup.domain.model.Signup
import com.solutionplus.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS


internal class SignupRepository  (
    private val remoteDs: ISignupRemoteDS,
    private val localDs: ISignupLocalDS,
) : ISignupRepository {

    override suspend fun signupWithPhone(signupRequest: SignupRequest): Signup {
        val result = remoteDs.signupWithPhone(signupRequest)
        return SignupMapper.dtoToDomain(result!!)
    }

    override suspend fun saveAccessToken(token: String) =
        localDs.saveAccessToken(token)

}