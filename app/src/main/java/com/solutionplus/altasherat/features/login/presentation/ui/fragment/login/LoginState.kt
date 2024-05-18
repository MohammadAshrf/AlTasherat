package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import com.solutionplus.altasherat.features.login.domain.model.User

sealed class LoginState {
    data class Success(val user: User?) : LoginState()
    object Loading : LoginState()
    data class Error(val message: String) : LoginState()
}
