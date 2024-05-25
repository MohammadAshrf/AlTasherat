package com.solutionplus.altasherat.features.signup.data.model.request

import android.util.Patterns
import com.google.gson.annotations.SerializedName

data class Phone(
    @SerializedName("country_code")
    val countryCode : String,
//    val extension: Any?= null,
//    @SerializedName("holder_name")
//    val holderName: Any?= null,
//    val id: Int ?= null,
    @SerializedName("number")
    val number : String ,
//    val type: Any?= null
){
    fun validatePhone():Boolean {
        return !(number.any(){!it.isDigit()} || number.isBlank() || number.length < 9 || number.length > 15 )
    }
}

