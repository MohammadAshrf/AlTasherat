package com.solutionplus.altasherat.feature.services.country.domain.repository.remote

import com.solutionplus.altasherat.feature.services.country.data.models.dto.CountriesResponse

interface ICountriesRemoteDS {
    suspend fun getCountiesFromRemote(): CountriesResponse
}