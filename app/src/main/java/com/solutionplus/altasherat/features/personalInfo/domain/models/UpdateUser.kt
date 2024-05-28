package com.solutionplus.altasherat.features.personalInfo.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UpdateUser(
	val message: String,
	val user: User
) : Parcelable