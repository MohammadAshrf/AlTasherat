package com.solutionplus.altasherat.feature.services.country.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.feature.services.country.domain.repository.local.ICountriesLocalDS

internal class CountriesLocalDS(private val localStorageProvider: IKeyValueStorageProvider) :
    ICountriesLocalDS {
    override suspend fun saveCountry(countryName: String) {
            localStorageProvider.saveEntry(StorageKeyEnum.COUNTRY_NAME, countryName, String::class.java)
    }
}