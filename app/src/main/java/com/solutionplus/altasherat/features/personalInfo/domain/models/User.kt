package com.solutionplus.altasherat.features.personalInfo.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val image: String,
    val firstName: String,
    val isBlocked: Int,
    val emailVerified: Boolean,
    val phoneVerified: Boolean,
    val phone: String,
    val birthDate: String,
    val middleName: String,
    val id: Int,
    val email: String,
    val userName: String,
    val lastName: String
) : Parcelable