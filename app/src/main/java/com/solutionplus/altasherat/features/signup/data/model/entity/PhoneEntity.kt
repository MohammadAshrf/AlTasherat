package com.solutionplus.altasherat.features.signup.data.model.entity

data class PhoneEntity(
    val countryCode: String,
    val number: String,
    val extension: String,
    val id: Int,
    val type: String,
    val holderName: String
)