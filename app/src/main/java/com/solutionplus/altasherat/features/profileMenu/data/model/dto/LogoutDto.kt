package com.solutionplus.altasherat.features.profileMenu.data.model.dto


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LogoutDto(
    @SerializedName("message")
    val message: String
) : Parcelable