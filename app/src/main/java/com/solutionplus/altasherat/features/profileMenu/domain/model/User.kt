package com.solutionplus.altasherat.features.profileMenu.domain.model

import android.os.Parcelable
import com.solutionplus.altasherat.features.personalInfo.domain.models.Image
import com.solutionplus.altasherat.features.personalInfo.domain.models.Phone
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val username: String,
    val firstname: String,
    val middleName: String,
    val lastname: String,
    val email: String,
    val phone: Phone,
    val image: Image,
    val birthdate: String,
    val emailVerified: Boolean,
    val phoneVerified: Boolean,
    val blocked: Int,
    val country: Country,
    val allPermissions: List<String>
) : Parcelable
