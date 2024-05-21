package com.solutionplus.altasherat.features.services.country.data.models

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.features.services.country.data.models.LinksItem

data class Meta(

    @field:SerializedName("path")
	val path: String? = null,

    @field:SerializedName("per_page")
	val perPage: Int? = null,

    @field:SerializedName("total")
	val total: Int? = null,

    @field:SerializedName("last_page")
	val lastPage: Int? = null,

    @field:SerializedName("from")
	val from: Int? = null,

    @field:SerializedName("links")
	val links: List<LinksItem?>? = null,

    @field:SerializedName("to")
	val to: Int? = null,

    @field:SerializedName("current_page")
	val currentPage: Int? = null
)