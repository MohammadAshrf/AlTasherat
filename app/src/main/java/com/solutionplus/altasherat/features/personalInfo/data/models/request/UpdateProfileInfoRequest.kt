package com.solutionplus.altasherat.features.personalInfo.data.models.request

import android.util.Patterns
import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.common.data.constants.Validation.BIRTH_DATE_MIN_AGE
import com.solutionplus.altasherat.common.data.constants.Validation.BIRTH_DATE_PATTERN
import com.solutionplus.altasherat.common.data.constants.Validation.IMAGE_DIV_SIZE
import com.solutionplus.altasherat.common.data.constants.Validation.IMAGE_MAX_SIZE
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
        return email.length <= 50 && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneValid(): Boolean {
        return phone.isPhoneNumberValid()
    }

    fun isCountryValid(): Boolean {
        return country > 0
    }


    fun isBirthDateValid(): Boolean {
        val formatter = DateTimeFormatter.ofPattern(BIRTH_DATE_PATTERN)
        val birthDate = LocalDate.parse(birthdate, formatter)
        val thirteenYearsAgo = LocalDate.now().minusYears(BIRTH_DATE_MIN_AGE.toLong())
        return !birthDate.isAfter(thirteenYearsAgo)
    }

    fun isImageValid(): Boolean {
        image?.let {
            // Define the maximum file size in KB
            val maxSizeInKB = IMAGE_MAX_SIZE
            // Check if the file size is less than or equal to 512 KB
            val sizeInKB = it.length() / IMAGE_DIV_SIZE
            return sizeInKB <= maxSizeInKB
        } ?: return true
    }
}