package com.solutionplus.altasherat.features.onboarding.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.onboarding.domain.repository.IOnBoardingRepository

class SetOnboardingShownUC(private val repository: IOnBoardingRepository) :
    BaseUseCase<Unit, Unit>() {
    override suspend fun execute(params: Unit?) {
        return repository.setOnBoardingShown()
    }
}