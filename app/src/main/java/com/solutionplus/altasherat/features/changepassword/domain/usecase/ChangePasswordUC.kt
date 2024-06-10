package com.solutionplus.altasherat.features.changepassword.domain.usecase

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.IchangePasswordRepository
import javax.inject.Inject


class ChangePasswordUC(
    private val repository: IchangePasswordRepository
) : BaseUseCase<Unit, ChangePasswordRequest>() {
     override suspend fun execute(params: ChangePasswordRequest?) {
        val errorMessages = params?.validateRequest()
        if (!errorMessages.isNullOrEmpty()) {
            throw LeonException.Local.RequestValidation(
                clazz = ChangePasswordRequest::class,
                errors = errorMessages
            )
        }
        repository.changePassword(params!!)
    }

    suspend fun token(): String? =
        repository.getAccessToKen()

    private fun ChangePasswordRequest.validateRequest(): Map<String, Int> {
        val errorKeys = mutableMapOf<String, Int>()

        if (!validateOldPassword()) {
            errorKeys[Validation.OLD_PASSWORD] = R.string.invalid_old_password
        }
        if (!validateNewPassword()) {
            errorKeys[Validation.NEW_PASSWORD] = R.string.invalid_new_password
        }
        if (!validateNewPasswordConfirmation()) {
            errorKeys[Validation.NEW_PASSWORD_CONFIRMATION] = R.string.invalid_new_password_confirmation
        }
        if (!validateNewPasswordEqualNewPasswordConfirmation()) {
            errorKeys[Validation.NEW_PASSWORD_EQUAL_CONFIRMATION] = R.string.new_password_and_confirmation_do_not_match
        }

        return errorKeys
    }
}