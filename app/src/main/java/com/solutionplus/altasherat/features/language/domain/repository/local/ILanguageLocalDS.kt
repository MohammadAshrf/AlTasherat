package com.solutionplus.altasherat.features.language.domain.repository.local

import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ILanguageLocalDS {
    suspend fun getSelectedCountry(): CountryEntity
    suspend fun saveSelectedCountry(country: Country)
    suspend fun getSelectedLanguage(): String
    suspend fun saveSelectedLanguage(language: String)
}