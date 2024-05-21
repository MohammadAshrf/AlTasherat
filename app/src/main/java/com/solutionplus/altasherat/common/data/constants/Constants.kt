package com.solutionplus.altasherat.common.data.constants

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val BASE_URL = "https://intern.api.altashirat.solutionplus.net/api/"

    val ACCESS_TOKEN = stringPreferencesKey("key_access_token")
    val USER_KEY = stringPreferencesKey("key_user")
    const val DS_File = "user_preferences"
    const val WORK_NAME = "updateListValuesWorker"
}