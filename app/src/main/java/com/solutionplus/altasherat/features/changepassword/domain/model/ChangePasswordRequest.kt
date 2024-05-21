package com.solutionplus.altasherat.features.changepassword.domain.model

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest (
    @SerializedName("old_password") val oldPassword: String?=null,
    @SerializedName("new_password") val newPassword: String?=null,
    @SerializedName("new_password_confirmation") val newPasswordConfirmation: String?=null,
    val token: String ?=null ,
)