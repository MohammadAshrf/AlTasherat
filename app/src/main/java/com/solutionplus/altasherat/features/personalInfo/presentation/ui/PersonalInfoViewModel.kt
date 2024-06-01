package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.language.domain.interactor.GetSelectedCountryUC
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageContract.LanguageAction.GetCountriesFromLocal
import com.solutionplus.altasherat.features.personalInfo.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserRequest
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetUserFromLocalUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetUserFromRemoteUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.HasUserUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.UpdateUserUC
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetSelectedCountryLocal
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedUser
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedUserFromLocal
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoState
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val getSelectedCountryUC: GetSelectedCountryUC,
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val hasUserUC: HasUserUC,
    private val getUserFromRemoteUC: GetUserFromRemoteUC,
    private val getUserFromLocalUC: GetUserFromLocalUC,
    private val updateUserUC: UpdateUserUC
) :
    AlTasheratViewModel<PersonalInfoAction, PersonalInfoEvent, PersonalInfoState>(PersonalInfoState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is GetCountriesFromLocal -> getCountriesFromLocal()
            is GetSelectedCountryLocal -> getSelectedCountryFromLocal()
            is GetUpdatedUser -> getUpdatedUserFromRemote()
            is GetUpdatedUserFromLocal -> getUpdatedUserFromLocal()

            is UpdateUser -> updateUser(
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

    private fun getUpdatedUser() {
        viewModelScope.launch {
            setState(oldViewState.copy(isLoading = true))

            hasUserUC.invoke().collect {
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
                        if (it.model) {
//                            sendEvent(PersonalInfoEvent.GetUpdatedUserSuccessfully("Profile data from local is up to date"))
                        }
                    }
                }
            }
        }
    }

    private fun getUpdatedUserFromLocal() {
        viewModelScope.launch {
            setState(oldViewState.copy(isLoading = true))
            getUserFromLocalUC.invoke().collect {
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
                        sendEvent(PersonalInfoEvent.GetUpdatedUserFromLocal(it.model))
                    }
                }
            }
        }
    }

    private fun getUpdatedUserFromRemote() {
        viewModelScope.launch {
            setState(oldViewState.copy(isLoading = true))
            getUserFromRemoteUC.invoke().collect {
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
                        sendEvent(PersonalInfoEvent.GetUpdatedUserSuccessfully(it.model))
                    }
                }
            }
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

    private fun getSelectedCountryFromLocal() {
        viewModelScope.launch {
            getSelectedCountryUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> sendEvent(
                        PersonalInfoEvent.GetSelectedCountryFromLocal(
                            country = it.model
                        )
                    )
                }
            }
        }
    }

    private fun getCountriesFromLocal() {
        viewModelScope.launch {
            getCountriesFromLocalUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> sendEvent(
                        PersonalInfoEvent.GetCountriesFromLocal(
                            countries = it.model
                        )
                    )
                }
            }
        }
    }


    override fun clearState() {
        setState(PersonalInfoState.initial())
    }
}
