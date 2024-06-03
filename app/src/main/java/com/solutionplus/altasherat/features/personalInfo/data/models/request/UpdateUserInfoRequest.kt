package com.solutionplus.altasherat.features.personalInfo.data.models.request

import android.util.Patterns
import com.google.gson.annotations.SerializedName

data class UpdateUserInfoRequest(
    @field:SerializedName("firstname") val firstname: String,
    @field:SerializedName("middlename") val middleName: String,
    @field:SerializedName("lastname") val lastname: String,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("phone") val phone: PhoneRequest,
    @field:SerializedName("image") val image: ImageRequest? = null,
    @field:SerializedName("birthdate") val birthdate: String,
    @field:SerializedName("country") val country: Int,
) {
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

    fun isBirthdateValid(): Boolean {
        return Regex(pattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$").matches(birthdate)
    }

    fun isPhoneValid(): Boolean {
        return phone.isCountryCodeValid() && phone.isPhoneNumberValid()
    }

    fun isCountryValid(): Boolean {
        return country > 0
    }


//        return !firstname.isNullOrEmpty() && !lastname.isNullOrEmpty() && !email.isNullOrEmpty() &&
//                phone?.number != null &&
//                !email.contains("@") && !email.contains(".")
}