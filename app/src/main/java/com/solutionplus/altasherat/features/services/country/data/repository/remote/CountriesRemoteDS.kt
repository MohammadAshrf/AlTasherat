package com.solutionplus.altasherat.features.services.country.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountriesResponse
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS

internal class CountriesRemoteDS(private val networkProvider: INetworkProvider) :
    ICountriesRemoteDS {
    override suspend fun getCountiesFromRemote(): CountriesResponse {
        return networkProvider.get(responseWrappedModel = CountriesResponse::class.java, "countries")
    }
}