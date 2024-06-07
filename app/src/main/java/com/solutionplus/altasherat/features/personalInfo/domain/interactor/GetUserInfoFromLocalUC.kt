package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserUC
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository

class GetUserInfoFromLocalUC(private val userUC: GetUserUC) :
    BaseUseCase<User, Unit>() {
    override suspend fun execute(params: Unit?): User {
       return userUC.execute(params)
    }
}