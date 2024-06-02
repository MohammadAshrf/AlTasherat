package com.solutionplus.altasherat.features.login.data.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phone")
    val phoneRequest : PhoneRequest ?= null,
    @SerializedName("password")
    val password : String ?= null,
){
    fun validatePassword():Boolean {
        return !(password.isNullOrBlank() ||password.length < 8 || password.length > 50)
    }

    fun validatePhone():Boolean {
        return phoneRequest!!.validatePhone()
    }
}
