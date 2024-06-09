package com.solutionplus.altasherat.features.services.country.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Country(
	val id: Int,
	val name: String,
	val code: String,
	val flag: String,
	val currency: String,
    val phoneCode: String
) : Parcelable
