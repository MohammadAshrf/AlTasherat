package com.solutionplus.altasherat.features.login.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Phone(
    val countryCode: String ?= null,
    val number: String?= null,
    val extension: String?= null,
    val id: Int?= null,
    val type: String?= null,
    val holderName: String?= null,
) : Parcelable