package com.solutionplus.altasherat.feature.services.country.data.repository

import com.solutionplus.altasherat.feature.services.country.data.mappers.CountryMapper
import com.solutionplus.altasherat.feature.services.country.data.models.dto.CountryDto
import com.solutionplus.altasherat.feature.services.country.domain.models.Country
import com.solutionplus.altasherat.feature.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.feature.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.feature.services.country.domain.repository.remote.ICountriesRemoteDS

class CountriesRepository(
    private val localDS: ICountriesLocalDS, private val remoteDS: ICountriesRemoteDS
) : ICountriesRepository {
    override suspend fun getCountriesFromRemote(): List<Country> {
        val result = remoteDS.getCountiesFromRemote().data
        return result?.map {
            CountryMapper.dtoToDomain(it ?: CountryDto())
        } ?: emptyList()
    }

    override suspend fun getCountriesFromLocal(): List<Country> {
        return CountryMapper.entityToDomain(localDS.getCountriesFromLocal())
    }

    override suspend fun saveCountries(countries: List<Country>) {
        localDS.saveCountriesToLocal(countries)
    }

    override suspend fun isOnBoardingShown(): Boolean {
        return localDS.isOnBoardingShown()
    }


}