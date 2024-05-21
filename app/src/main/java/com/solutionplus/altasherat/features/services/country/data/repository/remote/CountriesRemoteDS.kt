package com.solutionplus.altasherat.feature.services.country.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.feature.services.country.data.models.dto.CountryDto
import com.solutionplus.altasherat.feature.services.country.domain.repository.remote.ICountriesRemoteDS

internal class CountriesRemoteDS(private val networkProvider: INetworkProvider) :
    ICountriesRemoteDS {
    override suspend fun getCounties(): List<CountryDto> {
        return networkProvider.get(responseWrappedModel = CountryDto::class.java, "countries")
    }
}