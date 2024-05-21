package com.solutionplus.altasherat.features.changepassword.data.model.dto

import com.google.gson.annotations.SerializedName

data class ChangePasswordDto (
    @SerializedName("old_password") val oldPassword: String? = null,
    @SerializedName("new_password") val newPassword: String? = null,
    @SerializedName("new_password_confirmation") val newPasswordConfirmation: String? = null,
    val token: String? = null
)
