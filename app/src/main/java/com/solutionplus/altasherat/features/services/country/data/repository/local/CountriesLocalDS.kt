package com.solutionplus.altasherat.features.services.country.data.repository.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS

internal class CountriesLocalDS(private val localStorageProvider: IKeyValueStorageProvider) :
    ICountriesLocalDS {
    override suspend fun getCountriesLocal(): List<CountryEntity> {
        logger.info("start")
        val countries =
            localStorageProvider.getEntry(StorageKeyEnum.COUNTRIES, "", String::class.java)
        val itemType = object : TypeToken<List<CountryEntity>>() {}.type
        val result =Gson().fromJson<List<CountryEntity>>(countries, itemType)?: emptyList()
        logger.info("done $result")
        return result
    }

    override suspend fun saveCountriesToLocal(countries: List<Country>) {
        localStorageProvider
            .saveEntry(StorageKeyEnum.COUNTRIES, Gson().toJson(countries), String::class.java)
    }

    override suspend fun hasCountriesInLocal(): Boolean {
        val key = localStorageProvider.hasKey(StorageKeyEnum.COUNTRIES, false)
        logger.info(key.toString())
        return key
    }

    companion object {
        val logger = getClassLogger()
    }
}