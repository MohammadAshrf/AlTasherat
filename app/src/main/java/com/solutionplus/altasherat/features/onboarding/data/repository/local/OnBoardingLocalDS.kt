package com.solutionplus.altasherat.features.onboarding.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.onboarding.domain.repository.local.IOnBoardingLocalDS
import com.solutionplus.altasherat.features.services.country.data.repository.local.CountriesLocalDS

internal class OnBoardingLocalDS(private val localStorageProvider: IKeyValueStorageProvider) :
    IOnBoardingLocalDS {
    override suspend fun isOnBoardingShown(): Boolean {
        val key = localStorageProvider.hasKey(StorageKeyEnum.ONBOARDING_SHOWN, true)
        CountriesLocalDS.logger.info(key.toString())
        return key
    }

    override suspend fun setOnBoardingShown() {
        localStorageProvider.saveEntry(
            StorageKeyEnum.ONBOARDING_SHOWN, true, Boolean::class.java
        )
    }
}