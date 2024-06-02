package com.solutionplus.altasherat.features.login.domain.interactor.login

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import javax.inject.Inject

class LoginWithPhoneUC  (
    private val repository: ILoginRepository,
) : BaseUseCase<User, LoginRequest>(){
    public override suspend fun execute(params: LoginRequest?): User {
        validateRequest(params!!)?.let { message ->
            throw LeonException.Local.RequestValidation(
                clazz = LoginRequest::class,
                message = message
            )
        }
        val result = repository.loginWithPhone(params)
        repository.saveUser(result.userInfo)
        repository.saveAccessToken(result.accessToken)
        repository.getUser()
        return result.userInfo
    }


    private fun validateRequest(request: LoginRequest): String? {
        return request.run {
            when {
                !validatePhone() -> "PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long."
                !validatePassword() -> "Password is invalid. It must be between 8 and 50 characters."
                else -> null
            }
        }
    }
}