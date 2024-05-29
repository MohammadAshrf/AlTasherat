package com.solutionplus.altasherat.features.personalInfo.data.models.request

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.personalInfo.domain.models.Image
import com.solutionplus.altasherat.features.personalInfo.domain.models.Phone

data class UpdateUserRequest(
    @SerializedName("firstname") val firstName: String? = null,
    @SerializedName("middlename") val middleName: String? = null,
    @SerializedName("lastname") val lastName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("birth_date") val birthDate: String? = null,
    @SerializedName("phone") val phone: PhoneRequest? = null,
//    @SerializedName("image") val image: ImageRequest? = null,
    @SerializedName("country") val countryId: Int? = null
)