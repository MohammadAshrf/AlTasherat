package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository

class GetCountriesRemoteUC(private val repository: ICountriesRepository) :
    BaseUseCase<List<Country>, String?>() {
    override suspend fun execute(params: String?): List<Country> {
        val countries = repository.getCountriesRemote(params ?: "ar")
        repository.saveCountries(countries)
        return countries
    }
}