package com.solutionplus.altasherat.features.language.data.repository

import com.solutionplus.altasherat.features.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.language.domain.repository.local.ILanguageLocalDS
import com.solutionplus.altasherat.features.services.country.data.mappers.CountryMapper
import com.solutionplus.altasherat.features.services.country.domain.models.Country

class LanguageRepository(
    private val localDS: ILanguageLocalDS
) : ILanguageRepository {
    override suspend fun saveSelectedCountry(country: Country) {
        localDS.saveSelectedCountry(country)
    }

    override suspend fun getSelectedCountry(): Country {
        return CountryMapper.entityToDomain(localDS.getSelectedCountry())
    }

    override suspend fun saveSelectedLanguage(country: Country) {
        TODO("Not yet implemented")
    }

    override suspend fun getSelectedLanguage(): Country {
        TODO("Not yet implemented")
    }


}