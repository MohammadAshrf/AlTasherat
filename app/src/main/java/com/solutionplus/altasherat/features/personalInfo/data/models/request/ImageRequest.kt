package com.solutionplus.altasherat.features.personalInfo.data.models.request

import com.google.gson.annotations.SerializedName

data class ImageRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("path") val path: String,
    @SerializedName("title") val title: String,
)
