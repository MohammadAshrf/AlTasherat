package com.solutionplus.altasherat.features.services.language.data.repository.local

import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.services.language.domain.repository.local.ILanguageLocalDS
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

internal class LanguageLocalDS(private val localStorageProvider: IKeyValueStorageProvider) :
    ILanguageLocalDS {
    override suspend fun saveSelectedCountry(country: Country) {
        localStorageProvider
            .saveEntry(StorageKeyEnum.SELECTED_COUNTRY, Gson().toJson(country), String::class.java)
    }

    override suspend fun getSelectedCountry(): CountryEntity {
        val selectedCountry =
            localStorageProvider.getEntry(StorageKeyEnum.SELECTED_COUNTRY, "", String::class.java)
        return Gson().fromJson(selectedCountry, CountryEntity::class.java)
    }

    override suspend fun saveSelectedLanguage(language: String) {
        localStorageProvider
            .saveEntry(StorageKeyEnum.SELECTED_LANGUAGE,"", String::class.java)
    }

    override suspend fun getSelectedLanguage(): String {
        val selectedLanguage =
            localStorageProvider.getEntry(StorageKeyEnum.SELECTED_LANGUAGE, "", String::class.java)
        return selectedLanguage
    }

    //    override suspend fun getCountriesFromLocal(): List<CountryEntity> {
//        val countries =
//            localStorageProvider.getEntry(StorageKeyEnum.COUNTRIES, "", String::class.java)
//        return Gson().fromJson(countries, Array<CountryEntity>::class.java).toList()
//    }
//
//    override suspend fun saveCountriesToLocal(countries: List<Country>) {
//        localStorageProvider
//            .saveEntry(StorageKeyEnum.COUNTRIES, Gson().toJson(countries), String::class.java)
//    }
//
//    override suspend fun hasCountriesInLocal(): Boolean {
//        val key = localStorageProvider.hasKey(StorageKeyEnum.COUNTRIES, false)
//        logger.info(key.toString())
//        return key
    companion object {
        val logger = getClassLogger()
    }
}