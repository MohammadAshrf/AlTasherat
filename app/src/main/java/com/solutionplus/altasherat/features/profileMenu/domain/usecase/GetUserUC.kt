package com.solutionplus.altasherat.features.profileMenu.domain.usecase

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.domain.models.User

class GetUserUC(
    private val repository: IProfileMenuRepository
) : BaseUseCase<User, Unit>(){
    public override suspend fun execute(params: Unit?): User {
        if (repository.getIsUserLoggedIn()) {
            return repository.getUser()
        } else {
            throw IllegalStateException("User is not logged in")
        }
    }



}