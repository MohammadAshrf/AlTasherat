package com.solutionplus.altasherat.features.personalInfo.data.models.dto

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto

internal data class UpdateUserDto(
    @field:SerializedName("message") val message: String? = null,
    @field:SerializedName("user") val user: UserDto? = null
)