package com.solutionplus.altasherat.features.signup.presentation.ui

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.services.language.domain.interactor.GetSelectedCountryUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesLocalUC
import com.solutionplus.altasherat.features.signup.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.usecase.SignupUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUC: SignupUC,
    private val getCountriesUC: GetCountriesLocalUC,
    private val getSelectedCountryUC: GetSelectedCountryUC,
) : AlTasheratViewModel<SignUpContract.SignupActions, SignUpContract.SignupEvent, SignUpContract.SignUpState>(
    initialState = SignUpContract.SignUpState.initial()
) {

    init {
        fetchCountries()
        fetchSelectedCountry()
    }

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is SignUpContract.SignupActions.Signup -> signUp(
                action.firstName,
                action.lastName,
                action.email,
                action.phoneNumber,
                action.countryCode,
                action.countryId,
                action.password
            )

            is SignUpContract.SignupActions.GetSelectedCountry -> fetchSelectedCountry()
        }
    }


    private fun fetchCountries() {
        viewModelScope.launch {
            getCountriesUC.invoke().collect { resource ->
                when (resource) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = resource.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = resource.loading, exception = null))
                    is Resource.Success -> {
                        sendEvent(SignUpContract.SignupEvent.GetCountries(resource.model))
                    }
                }
            }
        }
    }

    private fun fetchSelectedCountry() {
        viewModelScope.launch {
            getSelectedCountryUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> {
                        sendEvent(SignUpContract.SignupEvent.GetSelectedCountry(it.model))
                    }
                }
            }
        }
    }

    private fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        countryCode: String,
        countryId: Int,
        password: String
    ) {
        viewModelScope.launch {
            val phone = PhoneRequest(
                countryCode = countryCode,
                number = phoneNumber
            )
            val signupRequest = SignupRequest(
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = phone,
                password = password,
                passwordConfirmation = password,
                countryId = countryId,
                countryCode = countryCode
            )

            signupUC.invoke(viewModelScope, signupRequest) { resource ->
                when (resource) {
                    is Resource.Failure -> {
                        setState(
                            oldViewState.copy(
                                isLoading = false,
                                exception = resource.exception
                            )
                        )
                    }

                    is Resource.Loading -> setState(oldViewState.copy(isLoading = resource.loading, exception = null))
                    is Resource.Success -> {
                        setState(oldViewState.copy(isLoading = false, exception = null))
                        sendEvent(SignUpContract.SignupEvent.SignupSuccess(resource.model))
                    }
                }
            }
        }
    }

    override fun clearState() {
        setState(SignUpContract.SignUpState.initial())
    }

}