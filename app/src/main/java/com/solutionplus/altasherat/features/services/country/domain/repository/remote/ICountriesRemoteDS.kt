package com.solutionplus.altasherat.features.services.country.domain.repository.remote

import com.solutionplus.altasherat.features.services.country.data.models.dto.CountryDto

internal interface ICountriesRemoteDS {
    suspend fun getCounties(): List<CountryDto>
}