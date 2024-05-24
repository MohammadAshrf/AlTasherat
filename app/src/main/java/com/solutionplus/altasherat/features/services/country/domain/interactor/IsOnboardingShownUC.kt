package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import kotlinx.coroutines.flow.Flow

class IsOnboardingShownUC(private val repository: ICountriesRepository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun execute(params: Unit?) : Boolean{
        return repository.isOnBoardingShown()
    }

    fun emitIsOnboardingShown(): Flow<Resource<Boolean>> = invoke()
}
