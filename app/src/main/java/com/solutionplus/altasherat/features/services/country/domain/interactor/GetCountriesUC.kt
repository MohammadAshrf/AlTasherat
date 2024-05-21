package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import kotlinx.coroutines.flow.Flow

class GetCountriesUC(private val repository: ICountriesRepository) :
    BaseUseCase<List<Country>, Unit>() {
    override suspend fun execute(params: Unit?): List<Country> {
        val result = repository.getCountries()
        repository.saveCountries(result)
        return result
    }

    fun emitCountries(): Flow<Resource<List<Country>>> = invoke()
}