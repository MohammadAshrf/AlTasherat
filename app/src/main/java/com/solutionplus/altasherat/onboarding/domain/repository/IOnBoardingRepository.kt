package com.solutionplus.altasherat.features.onboarding.domain.repository

interface IOnBoardingRepository {
    suspend fun isOnBoardingShown(): Boolean
    suspend fun setOnBoardingShown()
}