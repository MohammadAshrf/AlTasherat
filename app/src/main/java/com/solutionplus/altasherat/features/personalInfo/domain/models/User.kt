package com.solutionplus.altasherat.features.personalInfo.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class User(
	val image: Image,
	val firstname: String,
	val isBlocked: Int,
	val emailVerified: Boolean,
	val phoneVerified: Boolean,
	val phone: Phone,
	val birthDate: String,
	val middlename: String,
	val id: Int,
	val email: String,
	val username: String,
	val lastname: String
) : Parcelable