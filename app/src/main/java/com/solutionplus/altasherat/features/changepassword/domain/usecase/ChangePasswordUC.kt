package com.solutionplus.altasherat.features.changepassword.domain.usecase

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.IchangePasswordRepository
import javax.inject.Inject

class ChangePasswordUC @Inject constructor(
    private val repository: IchangePasswordRepository
) : BaseUseCase<Unit, ChangePasswordRequest>() {
    public override suspend fun execute(params: ChangePasswordRequest?) {
        params?.let { repository.changePassword(it) }
    }

    suspend fun token(): String? =
        repository.getAccessToKen()

}