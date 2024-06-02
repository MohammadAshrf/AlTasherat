package com.solutionplus.altasherat.features.personalInfo.data.models.entity


import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.PhoneDto
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

internal data class ImageEntity(
    val id: Int,
    val type: String,
    val path: String,
    val title: String,
    val updatedAt: String,
    val description: String,
    val createdAt: String,
    val main: Boolean,
    val priority: Int
)