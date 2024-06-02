package com.solutionplus.altasherat.features.personalInfo.data.models.entity


import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.PhoneDto
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

internal data class PhoneEntity(
    val id: Int,
    val countryCode: String,
    val number: String,
    val extension: String,
    val type: String,
    val holderName: String
)