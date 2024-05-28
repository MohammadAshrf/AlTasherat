package com.solutionplus.altasherat.features.profileMenu.domain.usecase

import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.profileMenu.domain.model.Logout
import com.solutionplus.altasherat.features.profileMenu.domain.model.User
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class LogoutUC(
    private val repository: IProfileMenuRepository
) : BaseUseCase<Logout, Unit>() {
    public override suspend fun execute(params: Unit? ,): Logout {
        repository.deleteUser()
        repository.deleteAccessToken()
        repository.changeUserLoginState(false)
        return repository.logout()
    }

}