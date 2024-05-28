package com.solutionplus.altasherat.features.personalInfo.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class ImageDto(
	@field:SerializedName("path") val path: String? = null,
	@field:SerializedName("updated_at") val updatedAt: Any? = null,
	@field:SerializedName("description") val description: Any? = null,
	@field:SerializedName("created_at") val createdAt: Any? = null,
	@field:SerializedName("main") val main: Boolean? = null,
	@field:SerializedName("id") val id: Int? = null,
	@field:SerializedName("type") val type: String? = null,
	@field:SerializedName("title") val title: String? = null,
	@field:SerializedName("priority") val priority: Int? = null
)