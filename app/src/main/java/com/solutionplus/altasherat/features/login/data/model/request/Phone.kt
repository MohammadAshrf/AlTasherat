package com.solutionplus.altasherat.features.login.data.model.request

import com.google.gson.annotations.SerializedName

data class Phone(
    @SerializedName("country_code")
    val countryCode : String,
    @SerializedName("number")
    val number : String ,
)
