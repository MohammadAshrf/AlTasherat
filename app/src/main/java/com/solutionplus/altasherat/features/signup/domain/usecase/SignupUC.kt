package com.solutionplus.altasherat.features.signup.domain.usecase

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.signup.data.model.request.Phone
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.model.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import javax.inject.Inject

class SignupUC(
    private val repository: ISignupRepository,

) : BaseUseCase<User, SignupRequest>() {

    public override suspend fun execute(params: SignupRequest?): User {
        val result = repository.signupWithPhone(params!!)
        repository.saveUser(result.user)
        repository.saveAccessToken(result.token)
        repository.getUser()

        validateRequest(params)?.let { message ->
            throw LeonException.Local.RequestValidation(
                clazz = ChangePasswordRequest::class,
                message = message
            )
        }
        return result.user
    }


    private fun validateRequest(request: SignupRequest): String? {
        return request.run {
            when {
                !validateFirstName() -> "First name is invalid. It must be between 3 and 15 characters."
                !validateLastName() -> "Last name is invalid. It must be between 3 and 15 characters."
                !validateEmail() -> "Email is invalid. It must be a valid email address and not exceed 50 characters."
                !validatePassword() -> "Password is invalid. It must be between 8 and 50 characters."
                !validatePhone() -> "Phone number is invalid. It must contain only digits and be between 9 and 15 characters long."
                else -> null
            }
        }
    }
}