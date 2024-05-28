package com.solutionplus.altasherat.features.deleteaccount.domain.usecase

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.IDeleteAccountRepository
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository

class DeleteAccountUC (
    private val repository: IDeleteAccountRepository
) :BaseUseCase<Unit, DeleteAccountRequest>(){
    override suspend fun execute(params: DeleteAccountRequest?) {
        params?.let {
            repository.deleteAccount(it)
            repository.deleteUser()
            repository.deleteAccessToken()
        }
    }
}