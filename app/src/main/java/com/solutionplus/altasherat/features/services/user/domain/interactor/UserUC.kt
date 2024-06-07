package com.solutionplus.altasherat.features.services.user.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository
import com.solutionplus.altasherat.features.services.user.domain.models.User

class UserUC (private val repository: IUserRepository) : BaseUseCase<User, User>(){
    public override suspend fun execute(params: User?): User {
        repository.saveUser(params!!)
        repository.getUser()
        return params
    }
}