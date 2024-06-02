package com.solutionplus.altasherat.features.personalInfo.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class AccountResponseDto(
    @field:SerializedName("user") val user: UserDto? = null
)