package com.solutionplus.altasherat.features.contactUs.presentation

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract

import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ContactUsContract {
    sealed class ContactUsAction : ViewAction {
        data object GetCountries : ContactUsAction()
        data object GetUpdatedUserFromLocal : ContactUsAction()

    }

    sealed class ContactUsEvent : ViewEvent {
        data class GetCountries(val country: List<Country>): ContactUsEvent()

        data class GetUpdatedUserFromLocal(val user: User) : ContactUsEvent()

    }


        data class ContactUsState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = ContactUsState(isLoading = false, exception = null, action = null)
        }
    }
}