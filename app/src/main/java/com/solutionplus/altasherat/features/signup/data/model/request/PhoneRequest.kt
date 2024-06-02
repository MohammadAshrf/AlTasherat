package com.solutionplus.altasherat.features.signup.data.model.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @field:SerializedName("country_code") val countryCode: String? = null,
    @field:SerializedName("number") val number: String? = null,
    @field:SerializedName("extension") val extension: String? = null,
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("type") val type: String? = null,
    @field:SerializedName("holder_name") val holderName: String? = null
){
    fun validatePhone():Boolean {
        return !(number!!.any(){!it.isDigit()} || number.isBlank() || number.length < 9 || number.length > 15 )
    }
}

