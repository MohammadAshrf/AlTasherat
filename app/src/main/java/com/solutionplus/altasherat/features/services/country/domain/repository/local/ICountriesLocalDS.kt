package com.solutionplus.altasherat.feature.services.country.domain.repository.local

internal interface ICountriesLocalDS {
    suspend fun saveCountry(countryName: String)
}