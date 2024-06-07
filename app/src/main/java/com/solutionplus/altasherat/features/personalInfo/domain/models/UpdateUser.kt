package com.solutionplus.altasherat.features.personalInfo.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.solutionplus.altasherat.features.services.user.domain.models.User

@Parcelize
data class UpdateUser(
	val message: String,
	val user: User
) : Parcelable