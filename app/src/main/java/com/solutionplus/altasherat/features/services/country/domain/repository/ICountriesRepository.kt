package com.solutionplus.altasherat.feature.services.country.domain.repository

import com.solutionplus.altasherat.feature.services.country.domain.models.Country

interface ICountriesRepository {
    suspend fun getCountries(): List<Country>
    suspend fun saveCountries(countries: List<Country>)
}