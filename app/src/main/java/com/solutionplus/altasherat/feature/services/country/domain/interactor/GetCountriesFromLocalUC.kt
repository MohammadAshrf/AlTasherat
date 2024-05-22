package com.solutionplus.altasherat.feature.services.country.domain.interactor

import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.feature.services.country.domain.models.Country
import com.solutionplus.altasherat.feature.services.country.domain.repository.ICountriesRepository
import kotlinx.coroutines.flow.Flow

class GetCountriesFromLocalUC(private val repository: ICountriesRepository) :
    BaseUseCase<List<Country>, Unit>() {
    override suspend fun execute(params: Unit?) : List<Country> {
        return repository.getCountriesFromLocal()
    }

    fun emitCountriesFromLocal(): Flow<Resource<List<Country>>> = invoke()
}
