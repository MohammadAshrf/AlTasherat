package com.solutionplus.altasherat.features.signup.data.model.entity


import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.PhoneDto
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

 data class PhoneEntity(
    val countryCode: String,
    val number: String,
    val extension: String,
    val id: Int,
    val type: String,
    val holderName: String
)