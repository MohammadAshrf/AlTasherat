package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository

class GetProfileInfoLocalUC(private val repository: IUpdateProfileRepository) :
    BaseUseCase<User, String>() {
    override suspend fun execute(params: String?): User {
        return repository.getProfileInfoLocal()
    }
}