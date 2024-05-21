package com.solutionplus.altasherat.feature.services.country.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Country(
	val id: Int,
	val name: String,
	val code: String,
	val visible: Boolean,
	val flag: String,
	val nationality: String,
	val currency: String,
    val phoneCode: String
) : Parcelable