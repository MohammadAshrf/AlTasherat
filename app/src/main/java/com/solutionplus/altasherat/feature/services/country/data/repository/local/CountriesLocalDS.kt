package com.solutionplus.altasherat.feature.services.country.data.repository.local

import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.feature.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.feature.services.country.domain.models.Country
import com.solutionplus.altasherat.feature.services.country.domain.repository.local.ICountriesLocalDS

internal class CountriesLocalDS(private val localStorageProvider: IKeyValueStorageProvider) :
    ICountriesLocalDS {
    override suspend fun getCountriesFromLocal(): List<CountryEntity> {
        val countries =
            localStorageProvider.getEntry(StorageKeyEnum.COUNTRIES, "", String::class.java)
        return Gson().fromJson(countries, Array<CountryEntity>::class.java).toList()
    }

    override suspend fun saveCountriesToLocal(countries: List<Country>) {
        localStorageProvider
            .saveEntry(StorageKeyEnum.COUNTRIES, Gson().toJson(countries), String::class.java)
    }

    override suspend fun isOnBoardingShown(): Boolean {

        val key= localStorageProvider.hasKey(StorageKeyEnum.ON_BOARDING_SHOWN, Boolean::class.java)
        logger.info(key.toString())
        return key

    }
    override suspend fun setOnBoardingShown(shown : Boolean) {
         localStorageProvider.saveEntry(StorageKeyEnum.ON_BOARDING_SHOWN, shown, Boolean::class.java)
        logger.info("key saved")
    }
    companion object{
        val logger = getClassLogger()
    }
}