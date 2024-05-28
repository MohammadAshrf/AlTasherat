package com.solutionplus.altasherat.features.onboarding.domain.repository.local

interface IOnBoardingLocalDS {
    suspend fun isOnBoardingShown(): Boolean
    suspend fun setOnBoardingShown()
}