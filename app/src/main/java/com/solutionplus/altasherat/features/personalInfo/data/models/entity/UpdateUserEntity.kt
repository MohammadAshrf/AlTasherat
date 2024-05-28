package com.solutionplus.altasherat.features.personalInfo.data.models.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

internal data class UpdateUserEntity(
	val message: String,
	val user: UserEntity
)