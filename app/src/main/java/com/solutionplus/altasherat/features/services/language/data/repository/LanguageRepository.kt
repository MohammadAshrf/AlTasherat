package com.solutionplus.altasherat.features.services.language.data.repository

import com.solutionplus.altasherat.features.services.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.services.language.domain.repository.local.ILanguageLocalDS

class LanguageRepository(
    private val localDS: ILanguageLocalDS
) : ILanguageRepository {
    override suspend fun saveSelectedLanguage(language: String) {
        localDS.saveSelectedLanguage(language)
    }

    override suspend fun getSelectedLanguage(): String {
        return localDS.getSelectedLanguage()
    }
}