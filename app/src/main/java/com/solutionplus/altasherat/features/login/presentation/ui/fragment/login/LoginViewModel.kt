package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.Phone
import com.solutionplus.altasherat.features.login.domain.interactor.login.LoginWithPhoneUC
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithPhoneUC: LoginWithPhoneUC
) : BaseViewModel<LoginState, LoginIntent>() {
    override val initialState: LoginState = LoginState.Loading
    override fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginWithPhone -> loginWithPhone(intent.phoneNumber,intent.countryCode,intent.password)
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
                    is Resource.Loading -> _viewState.update { LoginState.Loading }
                    is Resource.Success ->{
                        _viewState.update { LoginState.Success(resource.model) }
                    }
                    is Resource.Failure -> _viewState.update {
                        LoginState.Error(
                            resource.exception.message ?: "error in login"
                        )
                    }
                }
            }
        }
    }

}