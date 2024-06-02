package com.solutionplus.altasherat.features.services.country.data.repository

import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.features.services.country.data.mappers.CountryMapper
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS


class CountriesRepository(
    private val localDS: ICountriesLocalDS, private val remoteDS: ICountriesRemoteDS
) : ICountriesRepository {
    override suspend fun getCountriesFromRemote(params: String): List<Country> {
        val result = remoteDS.getCountiesFromRemote(params).data
        return CountryMapper.dtoToDomain(result)
    }

    override suspend fun getCountriesFromLocal(): List<Country> {
        getClassLogger().info("start repo")
        return CountryMapper.entityToDomain(localDS.getCountriesFromLocal())
    }
    override suspend fun saveCountries(countries: List<Country>) {
        localDS.saveCountriesToLocal(countries)
    }

    override suspend fun hasCountries(): Boolean {
        return localDS.hasCountriesInLocal()
    }
}