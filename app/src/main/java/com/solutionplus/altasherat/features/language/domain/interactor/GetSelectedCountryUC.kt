package com.solutionplus.altasherat.features.language.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.services.country.domain.models.Country

class GetSelectedCountryUC(private val repository: ILanguageRepository) :
    BaseUseCase<Country, String?>() {
    public override suspend fun execute(params: String?): Country {
        return repository.getSelectedCountry()
    }
}