package com.solutionplus.altasherat.features.login.domain.interactor.login

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import javax.inject.Inject

class LoginWithPhoneUC  (
    private val repository: ILoginRepository,
) : BaseUseCase<User, LoginRequest>(){
    public override suspend fun execute(params: LoginRequest?): User {
        val result = repository.loginWithPhone(params!!)
        repository.saveUser(result.userInfo)
        repository.saveAccessToken(result.accessToken)
        repository.getUser()

        validateRequest(params)?.let { message ->
            throw LeonException.Local.RequestValidation(
                clazz = ChangePasswordRequest::class,
                message = message
            )
        }
        return result.userInfo
    }


    private fun validateRequest(request: LoginRequest): String? {
        return request.run {
            when {
                !validatePassword() -> "Password is invalid. It must be between 8 and 50 characters."
                !validatePhone() -> "Phone number is invalid. It must contain only digits and be between 9 and 15 characters long."
                else -> null
            }
        }
    }
}
