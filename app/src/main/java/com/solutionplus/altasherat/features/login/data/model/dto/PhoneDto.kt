package com.solutionplus.altasherat.features.login.data.model.dto


import com.google.gson.annotations.SerializedName

data class PhoneDto(
    @SerializedName("country_code")
    val countryCode: String? = null,
    @SerializedName("extension")
    val extension: Any? = null,
    @SerializedName("holder_name")
    val holderName: Any? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("number")
    val number: String? = null,
    @SerializedName("type")
    val type: Any? = null
)