package com.solutionplus.altasherat.features.login.data.model.request

import android.util.Patterns
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phone")
    val phone : Phone,
    @SerializedName("password")
    val password : String,
){
    fun validatePassword():Boolean {
        return !(password.isBlank() ||password.length < 8 || password.length > 50)
    }

    fun validatePhone():Boolean {
        return phone.validatePhone()
    }
}
