package com.solutionplus.altasherat.features.signup.domain.usecase

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.model.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import com.solutionplus.altasherat.common.presentation.ui.extentions.validateRequest
class SignupUC(
    private val repository: ISignupRepository,

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
        repository.saveUser(result.user)
        repository.saveAccessToken(result.token)
        repository.getUser()
        return result.user
    }



}