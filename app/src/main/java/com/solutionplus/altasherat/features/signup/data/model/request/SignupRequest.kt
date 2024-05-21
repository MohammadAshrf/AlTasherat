package com.solutionplus.altasherat.features.signup.data.model.request

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.features.signup.data.model.request.Phone

data class SignupRequest(
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone : Phone,
    @SerializedName("password")
    val password : String,
    @SerializedName("password_confirmation")
    val passwordConfirmation : String,
    @SerializedName("country")
    val countryId : Int,
    @SerializedName("country_code")
    val countryCode: String
)
