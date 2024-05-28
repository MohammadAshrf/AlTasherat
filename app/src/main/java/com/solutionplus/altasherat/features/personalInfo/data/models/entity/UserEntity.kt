package com.solutionplus.altasherat.features.personalInfo.data.models.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.PhoneDto

internal data class UserEntity(
	val image: ImageDto,
	val firstname: String,
	val isBlocked: Int,
	val emailVerified: Boolean,
	val phoneVerified: Boolean,
	val phone: PhoneDto,
	val birthDate: String,
	val middlename: String,
	val id: Int,
	val email: String,
	val username: String,
	val lastname: String
)