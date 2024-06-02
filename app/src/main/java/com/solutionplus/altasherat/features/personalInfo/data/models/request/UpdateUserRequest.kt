package com.solutionplus.altasherat.features.personalInfo.data.models.request

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @field:SerializedName("firstname") val firstname: String? = null,
    @field:SerializedName("middlename") val middleName: String? = null,
    @field:SerializedName("lastname") val lastname: String? = null,
    @field:SerializedName("email") val email: String? = null,
    @field:SerializedName("phone") val phone: PhoneRequest? = null,
    @field:SerializedName("image") val image: ImageRequest? = null,
    @field:SerializedName("birthdate") val birthdate: String? = null,
    @field:SerializedName("country") val country: Int? = null,
)