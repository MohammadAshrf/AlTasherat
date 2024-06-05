package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.personalInfo.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetUserInfoFromLocalUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetUserInfoFromRemoteUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.UpdateUserInfoUC
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedUserFromLocal
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedUserFromRemote
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoState
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val getUserFromRemoteUC: GetUserInfoFromRemoteUC,
    private val getUserFromLocalUC: GetUserInfoFromLocalUC,
    private val updateUserUC: UpdateUserInfoUC
) :
    AlTasheratViewModel<PersonalInfoAction, PersonalInfoEvent, PersonalInfoState>(PersonalInfoState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is PersonalInfoAction.GetCountriesFromLocal -> getCountriesFromLocal()
            is GetUpdatedUserFromRemote -> getUpdatedUserFromRemote()
            is GetUpdatedUserFromLocal -> getUpdatedUserFromLocal()

            is UpdateUser -> updateUser(
                action.firstname,
                action.middleName,
                action.lastname,
                action.email,
                action.phone,
                action.image,
                action.birthdate,
                action.country,
            )
        }
    }

    private fun getUpdatedUserFromLocal() {
        viewModelScope.launch {
            getUserFromLocalUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(
                        oldViewState.copy(
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
            getUserFromRemoteUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(
                        oldViewState.copy(
                            exception = it.exception
                        )
                    )

                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> {
                        setState(oldViewState.copy(isLoading = false, exception = null))
                        sendEvent(PersonalInfoEvent.GetUpdatedUserFromRemote(it.model))
                    }
                }
            }
        }
    }

    private fun updateUser(
        firstname: String,
        middleName: String,
        lastname: String,
        email: String,
        phoneRequest: PhoneRequest,
        image: File?,
        birthdate: String,
        country: Int,
    ) {

        viewModelScope.launch {
            val phone = PhoneRequest(phoneRequest.countryCode, phoneRequest.number)
            val updateUserRequest = UpdateUserInfoRequest(
                firstname,
                middleName,
                lastname,
                email,
                phone,
                image,
                birthdate,
                country,
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

    private fun getCountriesFromLocal() {
        viewModelScope.launch {
            getCountriesFromLocalUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> {
                        sendEvent(
                            PersonalInfoEvent.GetCountriesFromLocal(
                                countries = it.model

                            )
                        )
                        getClassLogger().info("viewModel: ${it.model}")
                    }
                }
            }
        }
    }


    override fun clearState() {
        setState(PersonalInfoState.initial())
    }
}
