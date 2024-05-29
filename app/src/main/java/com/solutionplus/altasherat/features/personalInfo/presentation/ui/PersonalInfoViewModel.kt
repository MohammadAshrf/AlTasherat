package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.personalInfo.data.models.request.ImageRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserRequest
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.UpdateUserUC
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoState
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val updateUserUC: UpdateUserUC
) :
    AlTasheratViewModel<PersonalInfoAction, PersonalInfoEvent, PersonalInfoState>(PersonalInfoState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is PersonalInfoAction.GetCountriesFromLocal -> getCountries()
            is PersonalInfoAction.UpdateUser -> updateUser(
                action.firstName,
                action.middleName,
                action.lastName,
                action.email,
                action.birthDate,
                action.phoneNumber,
                action.countryCode,
                action.countryId,
//                action.imageId,
//                action.imageType,
//                action.imagePath,
//                action.imageTitle
            )
        }
    }

    private fun updateUser(
        firstName: String,
        middleName: String,
        lastName: String,
        email: String,
        birthDate: String,
        phoneNumber: String,
        countryCode: String,
        countryId: Int,
//        imageId: Int,
//        imageType: String,
//        imagePath: String,
//        imageTitle: String

    ) {

        viewModelScope.launch {
            val phone = PhoneRequest(countryCode, phoneNumber)
//            val image = ImageRequest(imageId, imageType, imagePath, imageTitle)
            val updateUserRequest = UpdateUserRequest(
                firstName,
                middleName,
                lastName,
                email,
                birthDate,
                phone,
//                image,
                countryId
            )

            setState(oldViewState.copy(isLoading = true))

            updateUserUC.invoke(updateUserRequest).collect {
                when (it) {
                    is Resource.Failure -> setState(
                        oldViewState.copy(
                            isLoading = false,
                            exception = it.exception
                        )
                    )

                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> {
                        setState(oldViewState.copy(isLoading = false, exception = null))
                        sendEvent(PersonalInfoEvent.UpdateDoneSuccessfully("Data updated successfully"))
                    }
                }
            }
        }
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCountriesFromLocalUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> sendEvent(PersonalInfoEvent.CountriesIndex(countries = it.model))
                }
            }
        }
    }


    override fun clearState() {
        setState(PersonalInfoState.initial())
    }
}
