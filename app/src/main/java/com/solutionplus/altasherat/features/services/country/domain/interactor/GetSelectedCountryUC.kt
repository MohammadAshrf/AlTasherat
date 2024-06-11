package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository

class GetSelectedCountryUC(private val repository: ICountriesRepository) :
    BaseUseCase<Country, String?>() {
    public override suspend fun execute(params: String?): Country {
        return repository.getSelectedCountry()
    }
}