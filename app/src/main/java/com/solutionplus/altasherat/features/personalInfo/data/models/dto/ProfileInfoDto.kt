package com.solutionplus.altasherat.features.personalInfo.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class ProfileInfoDto(
    @field:SerializedName("message") val message: String? = null,
    @field:SerializedName("user") val user: UserDto? = null
)