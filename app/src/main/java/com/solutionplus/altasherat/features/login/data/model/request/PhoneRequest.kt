package com.solutionplus.altasherat.features.login.data.model.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @SerializedName("country_code")
    val countryCode: String? = null,
    @SerializedName("number")
    val number: String? = null,
){
    fun validatePhone():Boolean {
        return !(number!!.any(){!it.isDigit()} || number.isBlank() || number.length < 9 || number.length > 15 )
    }
}
