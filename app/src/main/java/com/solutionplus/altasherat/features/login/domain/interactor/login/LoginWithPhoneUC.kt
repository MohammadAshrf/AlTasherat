package com.solutionplus.altasherat.features.login.domain.interactor.login

import com.google.gson.Gson
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase

class LoginWithPhoneUC(
    private val repository: ILoginRepository,
) : BaseUseCase<User, LoginRequest>() {
    public override suspend fun execute(params: LoginRequest?): User {
        val errorMessages = params?.validateRequest()
        if (!errorMessages.isNullOrEmpty()) {
            throw LeonException.Local.RequestValidation(
                clazz = LoginRequest::class,
                message = "message",
                errors = errorMessages
            )
        }
        val result = repository.loginWithPhone(params!!)
        repository.saveUser(result.userInfo)
        repository.saveAccessToken(result.accessToken)
        repository.getUser()
        return result.userInfo
    }
    private fun LoginRequest.validateRequest(): Map<String, Int> {
        val errorKeys = mutableMapOf<String, Int>()

        if (!validatePassword()) {
            errorKeys[Validation.PASSWORD] = R.string.invalid_password
        }
        if (!validatePhone()) {
            errorKeys[Validation.PHONE] = R.string.invalid_phone
        }

        return errorKeys
    }

}