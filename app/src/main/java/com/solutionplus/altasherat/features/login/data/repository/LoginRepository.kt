package com.solutionplus.altasherat.features.login.data.repository


import com.solutionplus.altasherat.features.login.data.mapper.LoginMapper
import com.solutionplus.altasherat.features.login.domain.model.Login
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.solutionplus.altasherat.features.login.domain.repository.remote.ILoginRemoteDS


internal class LoginRepository  (
    private val remoteDs: ILoginRemoteDS,
    private val localDs: ILoginLocalDS,
) : ILoginRepository {

    override suspend fun loginWithPhone(loginRequest: LoginRequest): Login {
        val result = remoteDs.loginWithPhone(loginRequest)
        return LoginMapper.dtoToDomain(result)
    }

    override suspend fun saveAccessToken(token: String) =
        localDs.saveAccessToken(token)

}