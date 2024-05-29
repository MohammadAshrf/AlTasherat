package com.solutionplus.altasherat.features.deleteaccount.domain.model.request

import com.google.gson.annotations.SerializedName

data class DeleteAccountRequest (
    val password : String?= null,

){
    fun validatePassword(): Boolean {
        return !password.isNullOrBlank()
    }
}