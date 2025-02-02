package com.solutionplus.altasherat.features.services.user.data.models.entity

import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import java.io.File


data class UserEntity(
    val id: Int,
    val username: String,
    val firstname: String,
    val middleName: String,
    val lastname: String,
    val email: String,
    val phone: PhoneEntity,
    val image: ImageEntity,
    val birthdate: String,
    val emailVerified: Boolean,
    val phoneVerified: Boolean,
    val blocked: Int,
    val country: CountryEntity,
    val allPermissions: List<String>
)
