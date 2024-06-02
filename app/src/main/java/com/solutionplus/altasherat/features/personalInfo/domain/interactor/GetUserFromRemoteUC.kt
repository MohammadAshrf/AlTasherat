package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository

//class GetUserFromRemoteUC(private val repository: IUpdateUserRepository) :
//    BaseUseCase<User, Unit>() {
//    override suspend fun execute(params: Unit?): User {
//        val user = repository.getUpdatedUserFromRemote()
//        logger.info(user.firstName)
//        return user
//    }
//    companion object{
//        val logger = getClassLogger()
//    }
//}