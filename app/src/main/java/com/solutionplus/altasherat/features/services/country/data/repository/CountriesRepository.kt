package com.solutionplus.altasherat.feature.services.country.data.repository

import com.google.gson.Gson
import com.solutionplus.altasherat.feature.services.country.data.mappers.CountryMapper
import com.solutionplus.altasherat.feature.services.country.domain.models.Country
import com.solutionplus.altasherat.feature.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.feature.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.feature.services.country.domain.repository.remote.ICountriesRemoteDS

internal class CountriesRepository(
    private val localDS: ICountriesLocalDS, private val remoteDS: ICountriesRemoteDS
) : ICountriesRepository {
    override suspend fun getCountries(): List<Country> {
        val result = remoteDS.getCounties()
        return CountryMapper.dtoToDomain(result)
    }

    override suspend fun saveCountries(countries: List<Country>) {
        val countriesEntity = CountryMapper.domainToEntity(countries)
        val result = Gson().toJson(countriesEntity)
        localDS.saveCountry(result)
    }
}