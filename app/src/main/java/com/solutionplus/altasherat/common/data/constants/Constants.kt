package com.solutionplus.altasherat.common.data.constants

object Constants {
    const val BASE_URL = "https://intern.api.altashirat.solutionplus.net/api/"
    const val EMAIL_KEY_BUNDLE = "emailKey"
    val NO_AUTHENTICATION = hashMapOf("No-Authentication" to "true")
}

object Validation {
    const val IMAGE = "image"
    const val BIRTH_DATE = "birthdate"
    const val OLD_PASSWORD = "old_password"
    const val NEW_PASSWORD = "new_password"
    const val NEW_PASSWORD_CONFIRMATION = "new_password_confirmation"
    const val NEW_PASSWORD_EQUAL_CONFIRMATION = "new_password_confirmation"
    const val COUNTRY = "country"
    const val FIRST_NAME = "firstname"
    const val MIDDLE_NAME = "middlename"
    const val LAST_NAME = "lastname"
    const val PASSWORD = "password"
    const val PHONE = "phone.number"
    const val EMAIL = "email"
    const val IMAGE_MAX_SIZE = 512
    const val IMAGE_DIV_SIZE = 1024
    const val BIRTH_DATE_PATTERN = "yyyy-MM-dd"
    const val BIRTH_DATE_MIN_AGE = 13
}