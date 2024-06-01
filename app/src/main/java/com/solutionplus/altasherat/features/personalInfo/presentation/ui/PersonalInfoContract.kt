package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface PersonalInfoContract {

    sealed class PersonalInfoAction : ViewAction {
        data class UpdateUser(
            val firstName: String,
            val middleName: String,
            val lastName: String,
            val email: String,
            val birthDate: String,
            val phoneNumber: String,
            val countryCode: String,
            val countryId: Int,
//            val imageId: Int?= null,
//            val imageType: String?= null,
//            val imagePath: String?= null,
//            val imageTitle: String?= null,
        ) : PersonalInfoAction()

        data object GetSelectedCountryLocal : PersonalInfoAction()
        data object GetCountriesFromLocal : PersonalInfoAction()
        data object GetUpdatedUser : PersonalInfoAction()
        data object GetUpdatedUserFromLocal : PersonalInfoAction()
    }

    sealed class PersonalInfoEvent : ViewEvent {
        data class GetSelectedCountryFromLocal(val country: Country) : PersonalInfoEvent()
        data class GetCountriesFromLocal(val countries: List<Country>) : PersonalInfoEvent()
        data class UpdateDoneSuccessfully(val message: String) : PersonalInfoEvent()
        data class UpdateFailed(val message: String) : PersonalInfoEvent()
        data class GetUpdatedUserSuccessfully(val user: User) : PersonalInfoEvent()
        data class GetUpdatedUserFromLocal(val user: User) : PersonalInfoEvent()
    }

    data class PersonalInfoState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = PersonalInfoState(isLoading = false, exception = null, action = null)
        }
    }
}