package com.solutionplus.altasherat.features.services.country.data.repository

import com.solutionplus.altasherat.features.services.country.data.mappers.CountryMapper
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS


class CountriesRepository(
    private val localDS: ICountriesLocalDS, private val remoteDS: ICountriesRemoteDS
) : ICountriesRepository {
    override suspend fun getCountriesRemote(params: String): List<Country> {
        val result = remoteDS.getCountriesRemote(params).data
        return CountryMapper.dtoToDomain(result)
    }

    override suspend fun getCountriesLocal(): List<Country> {
        return CountryMapper.entityToDomain(localDS.getCountriesLocal())
    }

    override suspend fun saveSelectedCountry(country: Country) {
        localDS.saveSelectedCountry(country)
    }

    override suspend fun getSelectedCountry(): Country {
        return CountryMapper.entityToDomain(localDS.getSelectedCountry())
    }

    override suspend fun saveCountries(countries: List<Country>) {
        localDS.saveCountriesToLocal(countries)
    }

    override suspend fun hasCountries(): Boolean {
        return localDS.hasCountriesInLocal()
    }
}