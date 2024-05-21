package com.solutionplus.altasherat.features.signup.data.model.request

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
)
