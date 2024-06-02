package com.solutionplus.altasherat.features.profileMenu.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Phone(
    val countryCode: String,
    val number: String,
    val extension: String,
    val id: Int,
    val type: String,
    val holderName: String
) : Parcelable