package com.solutionplus.altasherat.features.personalInfo.data.models.request

import com.google.gson.annotations.SerializedName

data class ImageRequest(
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("type") val type: String? = null,
    @field:SerializedName("path") val path: String? = null,
    @field:SerializedName("title") val title: String? = null,
    @field:SerializedName("updated_at") val updatedAt: String? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("created_at") val createdAt: String? = null,
    @field:SerializedName("main") val main: Boolean? = null,
    @field:SerializedName("priority") val priority: Int? = null
)