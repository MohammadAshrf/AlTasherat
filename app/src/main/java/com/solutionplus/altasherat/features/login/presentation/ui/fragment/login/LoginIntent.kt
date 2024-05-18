package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

sealed class LoginIntent {
    data class LoginWithPhone(val phoneNumber: String, val countryCode: String, val password: String) : LoginIntent()
}