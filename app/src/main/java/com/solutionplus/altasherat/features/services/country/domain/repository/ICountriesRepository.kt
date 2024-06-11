package com.solutionplus.altasherat.features.services.country.domain.repository

import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ICountriesRepository {
    suspend fun getCountriesRemote(params: String): List<Country>
    suspend fun getCountriesLocal(): List<Country>
    suspend fun saveSelectedCountry(country: Country)
    suspend fun getSelectedCountry(): Country
    suspend fun saveCountries(countries: List<Country>)
    suspend fun hasCountries(): Boolean
}