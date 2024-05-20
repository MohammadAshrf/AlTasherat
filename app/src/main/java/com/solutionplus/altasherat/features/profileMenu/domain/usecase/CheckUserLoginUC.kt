package com.solutionplus.altasherat.features.profileMenu.domain.usecase

import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository

class CheckUserLoginUC(
    private val repository: IProfileMenuRepository
) : BaseUseCase<UserEntity?, Unit>(){
    public override suspend fun execute(params: Unit?): UserEntity? {
        return repository.getUser()
    }



}