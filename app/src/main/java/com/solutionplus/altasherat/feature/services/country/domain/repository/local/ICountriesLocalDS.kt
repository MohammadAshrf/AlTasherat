package com.solutionplus.altasherat.feature.services.country.domain.repository.local

import com.solutionplus.altasherat.feature.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.feature.services.country.domain.models.Country

interface ICountriesLocalDS {
    suspend fun getCountriesFromLocal(): List<CountryEntity>
    suspend fun saveCountriesToLocal(countries: List<Country>)
    suspend fun isOnBoardingShown(): Boolean
}