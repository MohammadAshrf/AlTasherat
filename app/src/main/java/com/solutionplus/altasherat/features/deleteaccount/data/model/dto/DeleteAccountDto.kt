package com.solutionplus.altasherat.features.deleteaccount.data.model.dto

import com.google.gson.annotations.SerializedName

data class DeleteAccountDto(
    @SerializedName("password")
    val password : String?= null,
)
