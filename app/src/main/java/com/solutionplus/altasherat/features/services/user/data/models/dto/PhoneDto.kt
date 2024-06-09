package com.solutionplus.altasherat.features.services.user.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class PhoneDto(
	@field:SerializedName("country_code") val countryCode: String? = null,
	@field:SerializedName("number") val number: String? = null,
	@field:SerializedName("extension") val extension: String? = null,
	@field:SerializedName("id") val id: Int? = null,
	@field:SerializedName("type") val type: String? = null,
	@field:SerializedName("holder_name") val holderName: String? = null
)