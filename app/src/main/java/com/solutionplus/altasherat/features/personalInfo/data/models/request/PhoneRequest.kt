package com.solutionplus.altasherat.features.personalInfo.data.models.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @field:SerializedName("country_code") val countryCode: String? = null,
    @field:SerializedName("number") val number: String? = null,
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "country_code" to countryCode!!,
            "number" to number!!
        )
    }

    fun isPhoneNumberValid(): Boolean {
        return Regex("^(\\+?[0-9\\s\\-]{9,15})\$").matches(number!!)
    }
}