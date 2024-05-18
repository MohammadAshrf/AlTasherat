package com.solutionplus.altasherat.features.login.domain.interactor.login

import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import javax.inject.Inject

class LoginWithPhoneUC  @Inject constructor(
    private val repository: ILoginRepository,
) : BaseUseCase<User, LoginRequest>(){
    public override suspend fun execute(params: LoginRequest?): User {
        val result = repository.loginWithPhone(params!!)
        repository.saveUser(result.userInfo)
        repository.saveAccessToken(result.accessToken)
        repository.getUser()
        return result.userInfo
    }
}