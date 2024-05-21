package com.solutionplus.altasherat.feature.services.country.data.models

import com.google.gson.annotations.SerializedName

data class LinksItem(

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("url")
	val url: Any? = null
)