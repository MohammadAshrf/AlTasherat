package com.solutionplus.altasherat.features.personalInfo.data.models.request

import android.util.Patterns
import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.io.File



data class UpdateProfileInfoRequest(
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
        return Regex(pattern = "[a-zA-Z\u0600-\u06FF]{3,15}$").matches(firstname)
    }

    fun isMiddleNameValid(): Boolean {
        return Regex(pattern = "[a-zA-Z\u0600-\u06FF]{0,15}$").matches(middleName)
    }

    fun isLastNameValid(): Boolean {
        return Regex(pattern = "[a-zA-Z\u0600-\u06FF]{3,15}$").matches(lastname)
    }

    fun isEmailValid(): Boolean {
        return email.isNotBlank() && email.length <= 50 && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneValid(): Boolean {
        return phone.isPhoneNumberValid()
    }

    fun isCountryValid(): Boolean {
        return country > 0
    }



    fun isBirthDateValid(): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val birthDate = LocalDate.parse(birthdate, formatter)
        val thirteenYearsAgo = LocalDate.now().minusYears(13)
        return !birthDate.isAfter(thirteenYearsAgo)
    }

    fun isImageValid(): Boolean {
        image?.let {
            val maxSizeInKB = 512 // 10 MB
            // Check if the file size is less than or equal to 10MB
            val sizeInKB = it.length() / 1024
            return sizeInKB <= maxSizeInKB
        } ?: return true // If the image is null, return false
    }
}