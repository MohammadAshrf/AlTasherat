package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.services.language.domain.interactor.GetSelectedCountryUC
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.login.domain.interactor.login.LoginWithPhoneUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.signup.presentation.ui.SignUpContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithPhoneUC: LoginWithPhoneUC,
    private val getCountriesUC: GetCountriesFromLocalUC,
    private val getSelectedCountryUC: GetSelectedCountryUC
) : AlTasheratViewModel<LoginContract.LoginActions, LoginContract.LoginEvents, LoginContract.LoginState>(
    initialState = LoginContract.LoginState.initial()
) {



    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is LoginContract.LoginActions.LoginWithPhone -> loginWithPhone(
                action.phoneNumber,
                action.countryCode,
                action.password
            )

            is LoginContract.LoginActions.GetSelectedCountry ->  fetchSelectedCountry()

            is LoginContract.LoginActions.GetCountries -> fetchCountries()
        }
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            getCountriesUC.invoke().collect { resource ->
                when (resource) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = resource.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = resource.loading))
                    is Resource.Success -> {
                        sendEvent(LoginContract.LoginEvents.GetCountries(resource.model))
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
                        sendEvent(LoginContract.LoginEvents.GetSelectedCountry(it.model))
                    }
                }
            }
        }
    }

    private fun loginWithPhone(phoneNumber: String, countryCode: String, password: String) {
        val loginRequest = LoginRequest(
            phoneRequest = PhoneRequest(countryCode, phoneNumber),
            password = password
        )

        viewModelScope.launch {
            loginWithPhoneUC.invoke(viewModelScope, loginRequest) { resource ->
                when (resource) {
                    is Resource.Failure -> {
                        setState(oldViewState.copy(exception = resource.exception))

                    }
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = resource.loading , exception = null))
                    is Resource.Success -> sendEvent(LoginContract.LoginEvents.LoginSuccess(resource.model ))
                }
            }
        }
    }

    override fun clearState() {
        setState(LoginContract.LoginState.initial())
    }

}