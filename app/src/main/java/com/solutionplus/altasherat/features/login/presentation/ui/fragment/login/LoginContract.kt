package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.signup.presentation.ui.SignUpContract

interface LoginContract {

    sealed class LoginActions:ViewAction {
        data class LoginWithPhone(val phoneNumber: String, val countryCode: String, val password: String
        ) : LoginActions()

        data object FetchCountries : LoginActions()
    }

    sealed class LoginEvents:ViewEvent {
        data class LoginSuccess(val user: User) : LoginEvents()
//        data class LoginError(val message: String) : LoginEvents()
    }

    data class LoginState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = LoginState(isLoading = false, exception = null, action = null)
        }
    }
}