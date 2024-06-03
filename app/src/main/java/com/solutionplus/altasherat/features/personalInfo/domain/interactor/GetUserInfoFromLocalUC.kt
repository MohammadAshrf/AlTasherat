package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository

class GetUserInfoFromLocalUC(private val repository: IUpdateUserRepository) :
    BaseUseCase<User, String>() {
    override suspend fun execute(params: String?): User {
        return repository.getUpdatedUserFromLocal()
    }
}