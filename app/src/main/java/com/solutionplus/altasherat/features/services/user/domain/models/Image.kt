package com.solutionplus.altasherat.features.services.user.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Image(
	val id: Int,
	val type: String,
	val path: String,
	val title: String,
	val updatedAt: String,
	val description: String,
	val createdAt: String,
	val main: Boolean,
	val priority: Int
) : Parcelable