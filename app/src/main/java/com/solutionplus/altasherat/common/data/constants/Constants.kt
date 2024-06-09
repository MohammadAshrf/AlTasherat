package com.solutionplus.altasherat.common.data.constants

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val BASE_URL = "https://intern.api.altashirat.solutionplus.net/api/"
    const val EMAIL_KEY_BUNDLE = "emailKey"
    val NO_AUTHENTICATION = hashMapOf("No-Authentication" to "true")
}

object Validation {
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
}