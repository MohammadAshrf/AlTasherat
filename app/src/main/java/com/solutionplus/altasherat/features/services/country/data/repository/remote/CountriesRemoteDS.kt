package com.solutionplus.altasherat.features.services.country.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS

internal class CountriesRemoteDS(private val networkProvider: INetworkProvider) :
    ICountriesRemoteDS {
    override suspend fun getCounties(): List<CountryDto> {
        return networkProvider.get(responseWrappedModel = CountryDto::class.java, "countries")
    }
}