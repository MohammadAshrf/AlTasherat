package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCountriesUC(private val repository: ICountriesRepository) :
    BaseUseCase<List<Country>, Unit>() {
    override suspend fun execute(params: Unit?): List<Country> {
        return if (repository.isOnBoardingShown()) {
            repository.getCountriesFromLocal()
        } else {
            val remoteCountries = repository.getCountriesFromRemote()
            repository.saveCountries(remoteCountries)
            remoteCountries
        }
    }

    fun emitCountries(): Flow<Resource<List<Country>>> = flow {
        emit(Resource.Loading(true))
        try {
            val countries = execute(Unit)
            emit(Resource.Success(countries))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    companion object{
        val logger = getClassLogger()
    }
}
