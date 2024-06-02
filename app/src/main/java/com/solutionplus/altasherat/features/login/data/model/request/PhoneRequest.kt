package com.solutionplus.altasherat.features.login.data.model.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @SerializedName("country_code")
    val countryCode: String? = null,
    @SerializedName("extension")
    val extension: String? = null,
    @SerializedName("holder_name")
    val holderName: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("number")
    val number: String? = null,
    @SerializedName("type")
    val type: String? = null
){
    fun validatePhone():Boolean {
        return !(number!!.any(){!it.isDigit()} || number.isBlank() || number.length < 9 || number.length > 15 )
    }
}
