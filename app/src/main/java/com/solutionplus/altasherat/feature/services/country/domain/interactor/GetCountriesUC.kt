package com.solutionplus.altasherat.feature.services.country.domain.interactor

import com.solutionplus.altasherat.android.helpers.logging.Logger
import com.solutionplus.altasherat.android.helpers.logging.LoggerFactory
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.feature.services.country.domain.repository.ICountriesRepository
import kotlinx.coroutines.flow.Flow

class GetCountriesUC(private val repository: ICountriesRepository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun execute(params: Unit?): Boolean {
        return if (repository.isOnBoardingShown()) {
            true
        } else {
//            val remoteCountries = repository.getCountriesFromRemote()
//            repository.saveCountries(remoteCountries)
           val con = repository.getCountriesFromLocal()
            for (item in con) {
                logger.info("item: ${item.name}")
            }
            false
        }
    }

    fun emitCountries(): Flow<Resource<Boolean>> = invoke()

    companion object{
        val logger = getClassLogger()
    }
}
