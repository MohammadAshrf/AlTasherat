package com.solutionplus.altasherat.features.language.domain.repository

import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ILanguageRepository {
    suspend fun saveSelectedCountry(country: Country)
    suspend fun getSelectedCountry(): Country
    suspend fun saveSelectedLanguage(language: String)
    suspend fun getSelectedLanguage(): String
}