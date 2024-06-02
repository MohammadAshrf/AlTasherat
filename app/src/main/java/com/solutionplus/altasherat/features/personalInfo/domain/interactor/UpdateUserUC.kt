package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository

class UpdateUserUC(private val repository: IUpdateUserRepository) :
    BaseUseCase<UpdateUser, UpdateUserRequest>() {
    override suspend fun execute(params: UpdateUserRequest?): UpdateUser {
        val result = repository.updateUser(params ?: UpdateUserRequest())
        repository.saveUpdatedUser(result.user)
        return result
    }
}