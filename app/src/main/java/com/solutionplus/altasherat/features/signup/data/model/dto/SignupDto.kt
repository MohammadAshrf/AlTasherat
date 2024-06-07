package com.solutionplus.altasherat.features.signup.data.model.dto

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto

internal data class SignupDto (
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val user: UserDto? = null


)