package com.solutionplus.altasherat.features.signup.presentation.ui

import com.solutionplus.altasherat.features.signup.domain.model.User

sealed class SignUpState {
    data class Success(val user: User?) : SignUpState()
    object Loading : SignUpState()
    data class Error(val message: String) : SignUpState()
}