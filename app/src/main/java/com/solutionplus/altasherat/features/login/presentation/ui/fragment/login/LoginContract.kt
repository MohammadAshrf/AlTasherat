package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface LoginContract {

    sealed class LoginAction : ViewAction {
        data class LoginWithPhone(
            val phoneNumber: String, val countryCode: String, val password: String
        ) : LoginAction()

        data object GetCountries : LoginAction()
        data object GetSelectedCountry : LoginAction()
    }

    sealed class LoginEvents : ViewEvent {
        data class LoginSuccess(val user: User) : LoginEvents()

        data class GetCountries(val country: List<Country>) : LoginEvents()
        data class GetSelectedCountry(val country: Country) : LoginEvents()

    }

    data class LoginState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = LoginState(isLoading = false, exception = null, action = null)
        }
    }
}