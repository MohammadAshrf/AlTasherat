package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository

class GetUserInfoFromRemoteUC(private val repository: IUpdateUserRepository) :
    BaseUseCase<User, Unit>() {
    override suspend fun execute(params: Unit?): User {
        return repository.getUpdatedUserFromRemote()
    }
}