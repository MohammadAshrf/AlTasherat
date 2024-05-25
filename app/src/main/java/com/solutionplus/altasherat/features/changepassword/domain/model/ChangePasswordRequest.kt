package com.solutionplus.altasherat.features.changepassword.domain.model

import android.util.Patterns
import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest (
    @SerializedName("old_password") val oldPassword: String?=null,
    @SerializedName("new_password") val newPassword: String?=null,
    @SerializedName("new_password_confirmation") val newPasswordConfirmation: String?=null,
    val token: String ?=null ,
){
    fun validateOldPassword(): Boolean {
        return !oldPassword.isNullOrBlank() && oldPassword.length in 8..50
    }

    fun validateNewPassword(): Boolean {
        return !newPassword.isNullOrBlank() && newPassword.length in 8..50
    }

    fun validateNewPasswordConfirmation(): Boolean {
        return !newPasswordConfirmation.isNullOrBlank() && newPasswordConfirmation.length in 8..50
    }

    fun validateNewPasswordEqualNewPasswordConfirmation(): Boolean {
        return newPassword == newPasswordConfirmation
    }
}