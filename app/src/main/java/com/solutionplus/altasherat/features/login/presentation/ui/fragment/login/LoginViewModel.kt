package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.Phone
import com.solutionplus.altasherat.features.login.domain.interactor.login.LoginWithPhoneUC
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithPhoneUC: LoginWithPhoneUC
) : AlTasheratViewModel<LoginContract.LoginActions, LoginContract.LoginEvents, LoginContract.LoginState>(
    initialState = LoginContract.LoginState.initial()
) {

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is LoginContract.LoginActions.LoginWithPhone -> loginWithPhone(
                action.phoneNumber,
                action.countryCode,
                action.password
            )
        }
    }

    private fun loginWithPhone(phoneNumber: String, countryCode: String, password: String) {
        viewModelScope.launch {
            val loginRequest = LoginRequest(
                phone = Phone(countryCode, phoneNumber),
                password = password
            )
            loginWithPhoneUC.invoke(viewModelScope, loginRequest) { resource ->
                when (resource) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = resource.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading= resource.loading))
                    is Resource.Success -> sendEvent(LoginContract.LoginEvents.LoginSuccess(resource.model))

                }
                _isUserLoggedIn.value = false
            }
        }
    }

    override fun clearState() {
        setState(LoginContract.LoginState.initial())
        _isUserLoggedIn.value = false
    }

}