package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.personalInfo.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User
import java.io.File

interface PersonalInfoContract {

    sealed class PersonalInfoAction : ViewAction {
        data class UpdateUser(
            val firstname: String,
            val middleName: String,
            val lastname: String,
            val email: String,
            val phone: PhoneRequest,
            val image: File?,
            val birthdate: String,
            val country: Int,
        ) : PersonalInfoAction()

        data object GetCountriesLocal : PersonalInfoAction()
        data object GetUpdatedProfileRemote : PersonalInfoAction()
        data object GetUpdatedProfileLocal : PersonalInfoAction()
    }

    sealed class PersonalInfoEvent : ViewEvent {
        data class GetCountriesLocal(val countries: List<Country>) : PersonalInfoEvent()
        data class UpdateProfileSuccess(val message: String) : PersonalInfoEvent()
        data class UpdateProfileFailed(val message: String) : PersonalInfoEvent()
        data class GetUpdatedProfileRemote(val user: User) : PersonalInfoEvent()
        data class GetUpdatedProfileLocal(val user: User) : PersonalInfoEvent()

    }

    data class PersonalInfoState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = PersonalInfoState(isLoading = false, exception = null, action = null)
        }
    }
}