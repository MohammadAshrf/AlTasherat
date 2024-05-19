package com.solutionplus.altasherat.feature.services.country.domain.repository.remote

import com.solutionplus.altasherat.feature.services.country.data.models.dto.CountryDto

internal interface ICountriesRemoteDS {
    suspend fun getCounties(): List<CountryDto>
}