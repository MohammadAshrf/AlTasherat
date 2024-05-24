package com.solutionplus.altasherat.features.services.country.domain.repository.local

import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ICountriesLocalDS {
    suspend fun getArabicCountriesFromLocal(): List<CountryEntity>
    suspend fun getEnglishCountriesFromLocal(): List<CountryEntity>
    suspend fun saveArabicCountriesToLocal(countries: List<Country>)
    suspend fun saveEnglishCountriesToLocal(countries: List<Country>)
    suspend fun isOnBoardingShown(): Boolean
}