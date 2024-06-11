package com.solutionplus.altasherat.features.profileMenu.domain.usecase

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository

class CheckUserStateUC(private val repository: IProfileMenuRepository) :
    BaseUseCase<Boolean, Unit>() {
    public override suspend fun execute(params: Unit?): Boolean {
        return repository.getIsUserLoggedIn()
    }
}