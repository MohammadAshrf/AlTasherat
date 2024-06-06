package com.solutionplus.altasherat.features.services.language.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

class SaveSelectedCountryUC(private val repository: ILanguageRepository) :
    BaseUseCase<Unit, Country?>() {
    override suspend fun execute(params: Country?) {
        repository.saveSelectedCountry((params!!))
    }
}