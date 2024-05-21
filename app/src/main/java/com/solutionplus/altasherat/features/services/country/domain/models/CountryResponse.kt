package com.solutionplus.altasherat.features.services.country.domain.models

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountryDto

internal data class CountryResponse(
    @SerializedName("data") val countries: List<CountryDto>
)