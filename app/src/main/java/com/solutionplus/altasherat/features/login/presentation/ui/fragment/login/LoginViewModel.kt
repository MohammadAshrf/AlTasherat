package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.login.domain.interactor.login.LoginWithPhoneUC
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginContract.LoginAction.GetCountries
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginContract.LoginAction.GetSelectedCountry
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesLocalUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetSelectedCountryUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithPhoneUC: LoginWithPhoneUC,
    private val getCountriesLocalUC: GetCountriesLocalUC,
    private val getSelectedCountryUC: GetSelectedCountryUC
) : AlTasheratViewModel<LoginContract.LoginAction, LoginContract.LoginEvents, LoginContract.LoginState>(
    initialState = LoginContract.LoginState.initial()
) {
    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is LoginContract.LoginAction.LoginWithPhone -> loginWithPhone(
                action.phoneNumber,
                action.countryCode,
                action.password
            )

            is GetSelectedCountry -> fetchSelectedCountry()

            is GetCountries -> fetchCountries()
        }
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            getCountriesLocalUC.invoke().collect { resource ->
                when (resource) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = resource.exception))
                    is Resource.Loading -> setState(
                        oldViewState.copy(
                            isLoading = resource.loading,
                            exception = null
                        )
                    )

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
                    is Resource.Loading -> setState(
                        oldViewState.copy(
                            isLoading = it.loading,
                            exception = null
                        )
                    )

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

                    is Resource.Loading -> setState(
                        oldViewState.copy(
                            isLoading = resource.loading,
                            exception = null
                        )
                    )

                    is Resource.Success -> sendEvent(LoginContract.LoginEvents.LoginSuccess(resource.model))
                }
            }
        }
    }

    override fun clearState() {
        setState(LoginContract.LoginState.initial())
    }

}