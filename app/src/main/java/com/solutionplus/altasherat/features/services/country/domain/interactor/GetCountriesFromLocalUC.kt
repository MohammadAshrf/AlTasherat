package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import kotlinx.coroutines.flow.Flow

class GetCountriesFromLocalUC(private val repository: ICountriesRepository) :
    BaseUseCase<List<Country>, String?>() {
    override suspend fun execute(params: String?) : List<Country> {
      return repository.getCountriesFromLocal()
    }

    fun emitCountriesFromLocal(): Flow<Resource<List<Country>>> = invoke()
}
