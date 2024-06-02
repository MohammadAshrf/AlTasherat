package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository

class GetUserInfoUC(private val repository: IUpdateUserRepository) :
    BaseUseCase<UpdateUser, String>() {
    override suspend fun execute(params: String?): UpdateUser {
        return repository.getUpdatedUserFromLocal()
    }

}