package com.solutionplus.altasherat.features.deleteaccount.domain.usecase

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
    public override suspend fun execute(params: DeleteAccountRequest?) {
        params?.let {
            validateRequest(it)?.let { message ->
                throw LeonException.Local.RequestValidation(
                    clazz = ChangePasswordRequest::class,
                    message = message
                )
            }

            repository.deleteAccount(it)
            repository.deleteUser()
            repository.deleteAccessToken()
            repository.changeUserLoginState(false)

        }
    }

    private fun validateRequest(request: DeleteAccountRequest): String? {
        return request.run {
            when {
                !validatePassword() -> "password cannot be empty"
                else -> null
            }
        }
    }
}