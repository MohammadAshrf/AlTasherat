package com.solutionplus.altasherat.common.presentation.ui.extentions

import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.common.presentation.ui.extentions.Constants.EMAIL
import com.solutionplus.altasherat.common.presentation.ui.extentions.Constants.FIRST_NAME
import com.solutionplus.altasherat.common.presentation.ui.extentions.Constants.LAST_NAME
import com.solutionplus.altasherat.common.presentation.ui.extentions.Constants.PASSWORD
import com.solutionplus.altasherat.common.presentation.ui.extentions.Constants.PHONE
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest

fun SignupRequest.validateRequest(): Map<String, String> {
    val errorMessages = mutableMapOf<String, String>()

    if (!validateFirstName()) {
        errorMessages[FIRST_NAME] = "First name is invalid. It must be between 3 and 15 characters."
    }
    if (!validateLastName()) {
        errorMessages[LAST_NAME] = "Last name is invalid. It must be between 3 and 15 characters."
    }
    if (!validatePassword()) {
        errorMessages[PASSWORD] = "Password is invalid. It must be between 8 and 50 characters."
    }
    if (!validatePhone()) {
        errorMessages[PHONE] = "Phone number is invalid. It must contain only digits and be between 9 and 15 characters long."
    }
    if (!validateEmail()) {
        errorMessages[EMAIL] = "Email is invalid. It must be less than 50 characters."
    }

    return errorMessages
}

fun LoginRequest.validateRequest(): Map<String, String> {
    val errorMessages = mutableMapOf<String, String>()

    if (!validatePhone()) {
        errorMessages[PHONE] = "Phone number is invalid. It must contain only digits and be between 9 and 15 characters long."
    }

    if (!validatePassword()) {
        errorMessages[PASSWORD] = "Password is invalid. It must be between 8 and 50 characters."
    }

    return errorMessages
}

object Constants{
    const val FIRST_NAME = "firstName"
    const val LAST_NAME = "lastName"
    const val PASSWORD = "password"
    const val PHONE = "phone"
    const val EMAIL = "email"
}