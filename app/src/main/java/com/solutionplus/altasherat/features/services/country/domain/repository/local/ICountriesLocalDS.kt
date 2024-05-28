package com.solutionplus.altasherat.features.services.country.domain.repository.local

import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ICountriesLocalDS {
    suspend fun getCountriesFromLocal(): List<CountryEntity>
    suspend fun saveCountriesToLocal(countries: List<Country>)
    suspend fun hasCountriesInLocal(): Boolean
}