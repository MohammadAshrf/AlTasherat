package com.solutionplus.altasherat.features.personalInfo.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class UserDto(
    @field:SerializedName("image") val image: ImageDto? = null,
    @field:SerializedName("firstname") val firstName: String? = null,
    @field:SerializedName("is_blocked") val isBlocked: Int? = null,
    @field:SerializedName("email_verified") val emailVerified: Boolean? = null,
    @field:SerializedName("phone_verified") val phoneVerified: Boolean? = null,
    @field:SerializedName("phone") val phone: PhoneDto? = null,
    @field:SerializedName("birth_date") val birthDate: String? = null,
    @field:SerializedName("middlename") val middleName: String? = null,
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("email") val email: String? = null,
    @field:SerializedName("username") val userName: String? = null,
    @field:SerializedName("lastname") val lastName: String? = null
)