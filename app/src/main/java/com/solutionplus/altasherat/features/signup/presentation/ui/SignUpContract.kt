package com.solutionplus.altasherat.features.signup.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageContract
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.signup.domain.model.User

interface SignUpContract {

    sealed class SignupActions : ViewAction {
        data class Signup(
            val firstName: String,
            val lastName: String,
            val email: String,
            val phoneNumber: String,
            val countryCode: String,
            val countryId:Int,
            val password: String
        ) : SignupActions()
        data object GetSelectedCountry :SignupActions()
    }

    sealed class SignupEvent : ViewEvent {
        //        data class CountriesIndex(val countries: List<Country>) : SignupEvent()
        data class SignupSuccess(val user: User) : SignupEvent()
        data class GetSelectedCountry(val country: Country): SignupEvent()
//        data class SignupError(val message: String) : SignupEvent()
    }

    data class SignUpState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = SignUpState(isLoading = false, exception = null, action = null)
        }
    }
}