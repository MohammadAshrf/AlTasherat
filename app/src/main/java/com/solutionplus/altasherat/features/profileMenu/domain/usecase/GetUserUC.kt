package com.solutionplus.altasherat.features.profileMenu.domain.usecase

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.profileMenu.domain.model.User
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository

class GetUserUC(
    private val repository: IProfileMenuRepository
) : BaseUseCase<User, Unit>(){
    public override suspend fun execute(params: Unit?): User {
        if (repository.getIsUserLoggedIn()) {
            return repository.getUser()
        } else {
            return User(
                id = -1,
                userName = "",
                fullName = "",
                email = "",
                firstName = "",
                middleName = "",
                lastName = "",
                phone = "",
                birthDate = "",
                imageUrl = "",
                emailVerified = false
            )
        }
    }



}