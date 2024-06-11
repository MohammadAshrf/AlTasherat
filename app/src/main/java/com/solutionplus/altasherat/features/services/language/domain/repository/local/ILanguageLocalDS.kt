package com.solutionplus.altasherat.features.services.language.domain.repository.local

interface ILanguageLocalDS {
    suspend fun getSelectedLanguage(): String
    suspend fun saveSelectedLanguage(language: String)
}