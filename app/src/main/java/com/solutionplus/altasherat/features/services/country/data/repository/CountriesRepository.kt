package com.solutionplus.altasherat.features.services.country.data.repository

import com.solutionplus.altasherat.features.services.country.data.mappers.CountryMapper
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS

class CountriesRepository(
    private val localDS: ICountriesLocalDS, private val remoteDS: ICountriesRemoteDS
) : ICountriesRepository {
    override suspend fun getCountriesFromRemote(params: String): List<Country> {
        val result = remoteDS.getCountiesFromRemote(params).data
        return result?.map {
            CountryMapper.dtoToDomain(it ?: CountryDto())
        } ?: emptyList()
    }

    override suspend fun getArabicCountriesFromLocal(): List<Country> {
        return CountryMapper.entityToDomain(localDS.getArabicCountriesFromLocal())
    }

    override suspend fun getEnglishCountriesFromLocal(): List<Country> {
        return CountryMapper.entityToDomain(localDS.getEnglishCountriesFromLocal())
    }

    override suspend fun saveArabicCountries(countries: List<Country>) {
        localDS.saveArabicCountriesToLocal(countries)
    }

    override suspend fun saveEnglishCountries(countries: List<Country>) {
        localDS.saveArabicCountriesToLocal(countries)
    }

    override suspend fun isOnBoardingShown(): Boolean {
        return localDS.isOnBoardingShown()
    }


}