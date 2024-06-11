package com.solutionplus.altasherat.features.signup.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface SignUpContract {

    sealed class SignupAction : ViewAction {
        data class Signup(
            val firstName: String,
            val lastName: String,
            val email: String,
            val phoneNumber: String,
            val countryCode: String,
            val countryId:Int,
            val password: String
        ) : SignupAction()
        data object GetSelectedCountry :SignupAction()
        data object GetCountries : SignupAction()

    }

    sealed class SignupEvent : ViewEvent {
        data class SignupSuccess(val user: User) : SignupEvent()
        data class GetCountries(val country: List<Country>): SignupEvent()
        data class GetSelectedCountry(val country: Country): SignupEvent()
    }

    data class SignUpState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = SignUpState(isLoading = false, exception = null, action = null)
        }
    }
}