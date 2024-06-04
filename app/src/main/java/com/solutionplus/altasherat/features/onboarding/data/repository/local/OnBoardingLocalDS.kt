package com.solutionplus.altasherat.features.onboarding.data.repository.local

import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.onboarding.domain.repository.local.IOnBoardingLocalDS

internal class OnBoardingLocalDS(private val localStorageProvider: IKeyValueStorageProvider) :
    IOnBoardingLocalDS {
    override suspend fun isOnBoardingShown(): Boolean {
        val key = localStorageProvider.hasKey(StorageKeyEnum.ONBOARDING_SHOWN, false)
        logger.info(key.toString())
        return key
    }

    override suspend fun setOnBoardingShown() {
        val key = localStorageProvider.saveEntry(
            StorageKeyEnum.ONBOARDING_SHOWN, true, Boolean::class.java
        )
        logger.info(key.toString())
    }

    companion object {
        val logger = getClassLogger()
    }
}