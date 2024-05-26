package com.solutionplus.altasherat.features.onboarding.data.repository

import com.solutionplus.altasherat.features.onboarding.domain.repository.IOnBoardingRepository
import com.solutionplus.altasherat.features.onboarding.domain.repository.local.IOnBoardingLocalDS

class OnBoardingRepository(private val localDS: IOnBoardingLocalDS) : IOnBoardingRepository {
    override suspend fun isOnBoardingShown(): Boolean {
        return localDS.isOnBoardingShown()
    }

    override suspend fun setOnBoardingShown() {
        localDS.setOnBoardingShown()
    }
}