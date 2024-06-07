package com.solutionplus.altasherat.features.signup.domain.usecase

import com.google.gson.Gson
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
class SignupUC(
    private val repository: ISignupRepository,
    private val saveUserUC: SaveUserUC,
    private val getUserUC : GetUserUC
    ) : BaseUseCase<User, SignupRequest>() {

    public override suspend fun execute(params: SignupRequest?): User {
        val errorMessages = params?.validateRequest()
        if (!errorMessages.isNullOrEmpty()) {
            val message = Gson().toJson(errorMessages)
            throw LeonException.Local.RequestValidation(
                clazz = SignupRequest::class,
                message = message
            )
        }
        val result = repository.signupWithPhone(params!!)
        repository.saveAccessToken(result.token)
        saveUserUC.execute(result.user)
        return  getUserUC.execute(Unit)
    }

    private fun SignupRequest.validateRequest(): Map<String, Int> {
        val errorKeys = mutableMapOf<String, Int>()

        if (!validateFirstName()) {
            errorKeys[Validation.FIRST_NAME] = R.string.invalid_first_name
        }
        if (!validateLastName()) {
            errorKeys[Validation.LAST_NAME] = R.string.invalid_last_name
        }
        if (!validatePassword()) {
            errorKeys[Validation.PASSWORD] = R.string.invalid_password
        }
        if (!validatePhone()) {
            errorKeys[Validation.PHONE] = R.string.invalid_phone
        }
        if (!validateEmail()) {
            errorKeys[Validation.EMAIL] = R.string.invalid_email
        }

        return errorKeys
    }

}