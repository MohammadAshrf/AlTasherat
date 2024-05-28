package com.solutionplus.altasherat.features.personalInfo.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Image(
	val path: String,
	val updatedAt: String,
	val description: String,
	val createdAt: String,
	val main: Boolean,
	val id: Int,
	val type: String,
	val title: String,
	val priority: Int
) : Parcelable