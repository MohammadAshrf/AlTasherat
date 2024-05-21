package com.solutionplus.altasherat.feature.services.country.data.models.entity

internal data class CountryEntity(
val id: Int,
val name: String,
val code: String,
val visible: Boolean,
val flag: String,
val nationality: String,
val currency: String,
val phoneCode: String
)
