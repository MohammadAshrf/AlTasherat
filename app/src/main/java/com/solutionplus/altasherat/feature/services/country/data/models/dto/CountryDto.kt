package com.solutionplus.altasherat.feature.services.country.data.models.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("code") val code: String? = null,
    @field:SerializedName("flag") val flag: String? = null,
    @field:SerializedName("currency") val currency: String? = null,
    @field:SerializedName("phone_code") val phoneCode: String? = null
)