package com.solutionplus.altasherat.common.data.constants

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val BASE_URL = "https://intern.api.altashirat.solutionplus.net/api/"
    const val EMAIL_KEY_BUNDLE = "emailKey"
    val NO_AUTHENTICATION = hashMapOf("No-Authentication" to "true")
}

object Validation{
    const val FIRST_NAME = "firstname"
    const val LAST_NAME = "lastname"
    const val PASSWORD = "password"
    const val PHONE = "phone.number"
    const val EMAIL = "email"
}