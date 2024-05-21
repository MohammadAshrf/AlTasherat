package com.solutionplus.altasherat.features.services.country.domain.repository

import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ICountriesRepository {
    suspend fun getCountries(): List<Country>
    suspend fun saveCountries(countries: List<Country>)
}