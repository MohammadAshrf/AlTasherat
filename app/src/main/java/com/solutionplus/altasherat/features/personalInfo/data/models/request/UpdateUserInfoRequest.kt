package com.solutionplus.altasherat.features.personalInfo.data.models.request

import android.util.Patterns
import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import java.io.File

data class UpdateUserInfoRequest(
    @field:SerializedName("firstname") val firstname: String,
    @field:SerializedName("middlename") val middleName: String,
    @field:SerializedName("lastname") val lastname: String,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("phone") val phone: PhoneRequest,
    @field:SerializedName("image") val image: File?,
    @field:SerializedName("birthdate") val birthdate: String,
    @field:SerializedName("country") val country: Int,
) {
    val remoteMap: RemoteRequest
        get() = RemoteRequest(
            requestBody = hashMapOf<String, Any>().apply {
                put("firstname", firstname)
                put("middlename", middleName)
                put("lastname", lastname)
                put("email", email)
                put("phone", phone.toMap())
                put("birthdate", birthdate)
                put("country", country)
            },
            requestBodyFiles = hashMapOf<String, List<File>>().apply {
                put("image", listOfNotNull(image))
            }
        )

    fun isFirstNameValid(): Boolean {
        return Regex(pattern = "[a-zA-Z]{3,15}$").matches(firstname)
    }

    fun isMiddleNameValid(): Boolean {
        return Regex(pattern = "[a-zA-Z]{0,15}$").matches(middleName)
    }

    fun isLastNameValid(): Boolean {
        return Regex(pattern = "[a-zA-Z]{3,15}$").matches(lastname)
    }

    fun isEmailValid(): Boolean {
        return email.length <= 50 && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneValid(): Boolean {
        return phone.isPhoneNumberValid()
    }

    fun isCountryValid(): Boolean {
        return country > 0
    }
}