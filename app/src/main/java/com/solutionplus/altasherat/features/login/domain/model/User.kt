package com.solutionplus.altasherat.features.login.domain.model

import com.solutionplus.altasherat.features.services.country.domain.models.Country

data class User(
    val id: Int ?= null,
    val username: String?= null,
    val firstname: String?= null,
    val middleName: String?= null,
    val lastname: String?= null,
    val email: String?= null,
    val phone: Phone?= null,
    val image: Image?= null,
    val birthdate: String?= null,
    val emailVerified: Boolean?= null,
    val phoneVerified: Boolean?= null,
    val blocked: Int?= null,
    val country: Country?= null,
    val allPermissions: List<String>?= null,

    )
