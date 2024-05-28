package com.solutionplus.altasherat.features.personalInfo.data.models.request

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.features.signup.data.model.request.Phone

data class UpdateUserRequest(
    @SerializedName("firstname") val firstName: String? = null,
    @SerializedName("lastname") val lastName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: Phone? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("password_confirmation") val passwordConfirmation: String? = null,
    @SerializedName("country") val countryId: Int? = null,
    @SerializedName("country_code") val countryCode: String? = null
)

