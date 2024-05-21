package com.solutionplus.altasherat.feature.services.country.data.models.dto

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.feature.services.country.data.models.Links
import com.solutionplus.altasherat.feature.services.country.data.models.Meta

data class CountriesResponse(
    @field:SerializedName("data")
    val data: List<CountryDto?>? = null,

    @field:SerializedName("meta")
    val meta: Meta? = null,

    @field:SerializedName("links")
    val links: Links? = null
)