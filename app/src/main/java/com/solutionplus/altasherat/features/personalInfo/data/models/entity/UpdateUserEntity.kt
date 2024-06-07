package com.solutionplus.altasherat.features.personalInfo.data.models.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity

internal data class UpdateUserEntity(
	val message: String,
	val user: UserEntity
)