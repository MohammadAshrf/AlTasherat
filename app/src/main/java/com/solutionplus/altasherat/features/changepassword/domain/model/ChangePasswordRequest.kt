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
        return !(oldPassword!!.isBlank() ||oldPassword.length < 8 || oldPassword.length > 50)
    }

    fun validateNewPassword(): Boolean {
        return !(newPassword!!.isBlank() || newPassword.length < 8 || newPassword.length > 50)


    }

    fun validateNewPasswordConfirmation(): Boolean {
        return !(newPasswordConfirmation!!.isBlank() || newPasswordConfirmation.length < 8|| newPasswordConfirmation.length > 50)
    }

    fun validateNewPasswordEqualNewPasswordConfirmation(): Boolean {
        return newPassword == newPasswordConfirmation
    }
}