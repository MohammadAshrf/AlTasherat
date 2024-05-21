package com.solutionplus.altasherat.features.services.country.domain.repository.local

internal interface ICountriesLocalDS {
    suspend fun saveCountry(countryName: String)
//    suspend fun getSavedCountries(): String?
}