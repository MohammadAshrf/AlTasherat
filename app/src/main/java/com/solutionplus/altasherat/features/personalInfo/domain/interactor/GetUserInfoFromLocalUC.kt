package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.UserUC
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository

class GetUserInfoFromLocalUC(private val userUC: UserUC) :
    BaseUseCase<User, User>() {
    override suspend fun execute(params: User?): User {
       return userUC.execute(params)
    }
}