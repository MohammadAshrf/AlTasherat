package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import kotlinx.coroutines.flow.Flow

class GetCountriesUC(private val repository: ICountriesRepository) :
    BaseUseCase<Unit, Unit>() {
    override suspend fun execute(params: Unit?) {
       val countries = repository.getCountriesFromRemote()
        repository.saveCountries(countries)
        //repository.getCountriesFromLocal()
    }

    fun emitCountries(): Flow<Resource<Unit>> = invoke()
}
