package com.solutionplus.altasherat.features.personalInfo.data.models.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("number") val number: String
)
