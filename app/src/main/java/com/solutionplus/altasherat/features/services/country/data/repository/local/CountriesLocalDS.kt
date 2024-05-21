package com.solutionplus.altasherat.features.services.country.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS

internal class CountriesLocalDS(private val localStorageProvider: IKeyValueStorageProvider) :
    ICountriesLocalDS {
    override suspend fun saveCountry(countryName: String) {
            localStorageProvider.saveEntry(StorageKeyEnum.COUNTRY_NAME, countryName, String::class.java)
    }
//    override suspend fun getSavedCountries(): String? {
//        return localStorageProvider.getEntry(StorageKeyEnum.COUNTRIES_LIST, String::class.java)
//    }
}