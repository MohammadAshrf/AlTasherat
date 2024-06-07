package com.solutionplus.altasherat.features.login.domain.interactor.login

import com.google.gson.Gson
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import com.solutionplus.altasherat.features.services.user.domain.models.User

class LoginWithPhoneUC(
    private val repository: ILoginRepository,
    private val saveUserUC: SaveUserUC,
    private val getUserUC : GetUserUC
) : BaseUseCase<User, LoginRequest>() {
    public override suspend fun execute(params: LoginRequest?): User {
        val errorMessages = params?.validateRequest()
        if (!errorMessages.isNullOrEmpty()) {
            val message = Gson().toJson(errorMessages)
            throw LeonException.Local.RequestValidation(
                clazz = LoginRequest::class,
                message = message
            )
        }
        val result = repository.loginWithPhone(params!!)
        repository.saveAccessToken(result.accessToken)
        saveUserUC.execute(result.userInfo)
        return  getUserUC.execute(Unit)
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