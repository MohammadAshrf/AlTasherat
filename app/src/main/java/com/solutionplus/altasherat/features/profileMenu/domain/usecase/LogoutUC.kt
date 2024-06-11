package com.solutionplus.altasherat.features.profileMenu.domain.usecase

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.profileMenu.domain.model.Logout
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository

class LogoutUC(
    private val repository: IProfileMenuRepository
) : BaseUseCase<Logout, Unit>() {
    public override suspend fun execute(params: Unit?): Logout {
        val logoutResponse = repository.logout()
        repository.deleteUser()
        repository.deleteAccessToken()
        repository.changeUserLoginState(false)
        return logoutResponse
    }

}