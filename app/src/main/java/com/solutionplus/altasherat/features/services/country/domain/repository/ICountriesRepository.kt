package com.solutionplus.altasherat.features.services.country.domain.repository

import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ICountriesRepository {
    suspend fun getCountriesFromRemote(params: String): List<Country>
    suspend fun getArabicCountriesFromLocal(): List<Country>
    suspend fun getEnglishCountriesFromLocal(): List<Country>
    suspend fun saveArabicCountries(countries: List<Country>)
    suspend fun saveEnglishCountries(countries: List<Country>)
    suspend fun isOnBoardingShown(): Boolean
}