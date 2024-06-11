package com.solutionplus.altasherat.features.services.language.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.services.language.domain.repository.local.ILanguageLocalDS

internal class LanguageLocalDS(private val localStorageProvider: IKeyValueStorageProvider) :
    ILanguageLocalDS {
    override suspend fun saveSelectedLanguage(language: String) {
        localStorageProvider
            .saveEntry(StorageKeyEnum.SELECTED_LANGUAGE, "", String::class.java)
    }

    override suspend fun getSelectedLanguage(): String {
        val selectedLanguage =
            localStorageProvider.getEntry(StorageKeyEnum.SELECTED_LANGUAGE, "", String::class.java)
        return selectedLanguage
    }
}