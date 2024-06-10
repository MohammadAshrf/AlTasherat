package com.solutionplus.altasherat.features.personalInfo.data.models.entity

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity

internal data class UpdateProfileInfoEntity(
	val message: String,
	val user: UserEntity
)