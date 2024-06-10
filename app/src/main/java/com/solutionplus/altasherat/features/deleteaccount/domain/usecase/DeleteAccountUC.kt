package com.solutionplus.altasherat.features.deleteaccount.domain.usecase

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.IDeleteAccountRepository
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest

class DeleteAccountUC (
    private val repository: IDeleteAccountRepository
) :BaseUseCase<Unit, DeleteAccountRequest>(){
     override suspend fun execute(params: DeleteAccountRequest?) {
        params?.let {
            val errorMessages = it.validateRequest()
            if (errorMessages.isNotEmpty()) {
                throw LeonException.Local.RequestValidation(
                    clazz = DeleteAccountRequest::class,
                    errors = errorMessages
                )
            }

            repository.deleteAccount(it)
            repository.deleteUser()
            repository.deleteAccessToken()
            repository.changeUserLoginState(false)
        }
    }

    private fun DeleteAccountRequest.validateRequest(): Map<String, Int> {
        val errorKeys = mutableMapOf<String, Int>()

        if (!validatePassword()) {
            errorKeys[Validation.PASSWORD] = R.string.invalid_password
        }

        return errorKeys
    }

}