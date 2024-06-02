package com.solutionplus.altasherat.features.profileMenu.data.model.entity

internal data class PhoneEntity(
    val countryCode: String,
    val number: String,
    val extension: String,
    val id: Int,
    val type: String,
    val holderName: String
)