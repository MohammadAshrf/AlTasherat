package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository

class SaveSelectedCountryUC(private val repository: ICountriesRepository) :
    BaseUseCase<Unit, Country?>() {
    override suspend fun execute(params: Country?) {
        repository.saveSelectedCountry((params!!))
    }
}