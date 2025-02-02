package com.solutionplus.altasherat.features.services.user.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository

class SaveUserUC(private val repository: IUserRepository) : BaseUseCase<Unit, User>() {
    public override suspend fun execute(params: User?) {
        repository.saveUser(params!!)
    }
}