package com.solutionplus.altasherat.features.changepassword.domain.usecase

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.IchangePasswordRepository
import javax.inject.Inject

class ChangePasswordUC(
    private val repository: IchangePasswordRepository
) : BaseUseCase<Unit, ChangePasswordRequest>() {
    public override suspend fun execute(params: ChangePasswordRequest?) {
        validateRequest(params!!)?.let { message ->
            throw LeonException.Local.RequestValidation(
                clazz = ChangePasswordRequest::class,
                message = message
            )
        }
        repository.changePassword(params)
    }

    suspend fun token(): String? =
        repository.getAccessToKen()


    private fun validateRequest(request: ChangePasswordRequest): String? {
        return request.run {
            when {
                !validateOldPassword() -> "Old password is invalid. It must be between 8 and 50 characters."
                !validateNewPassword() -> "New password is invalid. It must be between 8 and 50 characters."
                !validateNewPasswordConfirmation() -> "New password confirmation is invalid. It must be between 8 and 50 characters."
                !validateNewPasswordEqualNewPasswordConfirmation() -> "New password and confirmation do not match."
                else -> null
            }
        }
    }
}