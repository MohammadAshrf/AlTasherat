package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageContract
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.signup.presentation.ui.SignUpContract

interface LoginContract {

    sealed class LoginActions:ViewAction {
        data class LoginWithPhone(val phoneNumber: String, val countryCode: String, val password: String
        ) : LoginActions()

        data object GetCountries : LoginActions()
        data object GetSelectedCountry : LoginActions()
    }

    sealed class LoginEvents:ViewEvent {
        data class LoginSuccess(val user: User) : LoginEvents()

        data class GetCountries(val country: List<Country>): LoginEvents()
        data class GetSelectedCountry(val country: Country): LoginEvents()

        data class LoginFailure(val exception: LeonException) : LoginEvents()
    }

    data class LoginState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = LoginState(isLoading = false, exception = null, action = null)
        }
    }
}