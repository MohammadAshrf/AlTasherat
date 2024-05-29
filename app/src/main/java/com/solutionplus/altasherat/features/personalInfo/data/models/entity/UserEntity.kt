package com.solutionplus.altasherat.features.personalInfo.data.models.entity


import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.PhoneDto

internal data class UserEntity(
    val image: String,
    val firstName: String,
    val isBlocked: Int,
    val emailVerified: Boolean,
    val phoneVerified: Boolean,
    val phone: String,
    val birthDate: String,
    val middleName: String,
    val id: Int,
    val email: String,
    val userName: String,
    val lastName: String
)