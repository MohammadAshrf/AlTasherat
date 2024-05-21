package com.solutionplus.altasherat.feature.services.country.domain.repository

import com.solutionplus.altasherat.feature.services.country.domain.models.Country

interface ICountriesRepository {
    suspend fun getCountriesFromRemote(): List<Country>
    suspend fun getCountriesFromLocal(): List<Country>
    suspend fun saveCountries(countries: List<Country>)
    suspend fun isOnBoardingShown(): Boolean
    suspend fun setOnBoardingShown(shown: Boolean)

}