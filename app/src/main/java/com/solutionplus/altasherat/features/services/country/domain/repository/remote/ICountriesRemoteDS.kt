package com.solutionplus.altasherat.features.services.country.domain.repository.remote

import com.solutionplus.altasherat.features.services.country.data.models.dto.CountriesResponse

interface ICountriesRemoteDS {
    suspend fun getCountiesFromRemote(): CountriesResponse
}