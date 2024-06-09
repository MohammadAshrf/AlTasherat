package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository

class GetProfileInfoRemoteUC(private val repository: IUpdateProfileRepository) :
    BaseUseCase<User, Unit>() {
    override suspend fun execute(params: Unit?): User {
        return repository.getProfileInfoRemote()
    }
}