package com.solutionplus.altasherat.features.services.user.data.models.dto

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountryDto
import okhttp3.MultipartBody
import java.io.File

internal data class UserDto(
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("username") val username: String? = null,
    @field:SerializedName("firstname") val firstname: String? = null,
    @field:SerializedName("middlename") val middleName: String? = null,
    @field:SerializedName("lastname") val lastname: String? = null,
    @field:SerializedName("email") val email: String? = null,
    @field:SerializedName("phone") val phone: PhoneDto? = null,
    @field:SerializedName("image") val image: ImageDto? = null,
    @field:SerializedName("birthdate") val birthdate: String? = null,
    @field:SerializedName("email_verified") val emailVerified: Boolean? = null,
    @field:SerializedName("phone_verified") val phoneVerified: Boolean? = null,
    @field:SerializedName("blocked") val blocked: Int? = null,
    @field:SerializedName("country") val country: CountryDto? = null,
    @field:SerializedName("all_permissions") val allPermissions: List<String>? = null
)