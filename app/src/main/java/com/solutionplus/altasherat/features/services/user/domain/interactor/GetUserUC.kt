package com.solutionplus.altasherat.features.services.user.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository

class GetUserUC(private val repository: IUserRepository) : BaseUseCase<User, Unit>() {
    public override suspend fun execute(params: Unit?): User {
        return repository.getUser()
    }
}