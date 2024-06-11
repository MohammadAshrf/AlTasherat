package com.solutionplus.altasherat.features.services.language.domain.repository

interface ILanguageRepository {
    suspend fun saveSelectedLanguage(language: String)
    suspend fun getSelectedLanguage(): String
}