package com.solutionplus.altasherat.features.onboarding.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.onboarding.domain.repository.IOnBoardingRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository

class IsOnboardingShownUC(private val repository: IOnBoardingRepository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun execute(params: Unit?): Boolean {
        return repository.isOnBoardingShown()
    }
}