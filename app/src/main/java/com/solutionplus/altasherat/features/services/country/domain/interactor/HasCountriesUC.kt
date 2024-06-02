package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository

class HasCountriesUC(
    private val countriesRepository: ICountriesRepository
) : BaseUseCase<Boolean, Unit>() {
    override suspend fun execute(params: Unit?): Boolean {
        return countriesRepository.hasCountries()
    }
}