package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository

class GetCountriesUC(private val repository: ICountriesRepository) :
    BaseUseCase<Unit, Unit>() {
    override suspend fun execute(params: Unit?) {

        val arabicCountries = repository.getCountriesFromRemote("ar")
        val englishCountries = repository.getCountriesFromRemote("en")

        repository.saveArabicCountries(arabicCountries)
        repository.saveEnglishCountries(englishCountries)
        //repository.getCountriesFromLocal()
    }
}
