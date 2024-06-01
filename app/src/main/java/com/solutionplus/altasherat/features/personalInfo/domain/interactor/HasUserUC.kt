package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository

class HasUserUC(
    private val repository: IUpdateUserRepository
) : BaseUseCase<Boolean, Unit>() {
    override suspend fun execute(params: Unit?): Boolean {
        return repository.hasUser()
    }
}