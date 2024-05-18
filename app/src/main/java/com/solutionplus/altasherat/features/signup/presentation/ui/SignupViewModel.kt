package com.solutionplus.altasherat.features.signup.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.ui.base.viewmodel.BaseViewModel
import com.solutionplus.altasherat.features.countries.country.Country
import com.solutionplus.altasherat.features.signup.data.model.request.Phone
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.usecase.SignupUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUC: SignupUC,
) : BaseViewModel<SignUpState, SignUpIntent>() {
    override val initialState: SignUpState = SignUpState.Loading

    override fun handleIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.SignUp -> signUp(
                intent.firstName,
                intent.lastName,
                intent.email,
                intent.phoneNumber,
                intent.countryCode,
                intent.password
            )
        }
    }

    private fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        countryCode: String,
        password: String
    ) {
        viewModelScope.launch {
            val phone = Phone(
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
                countryId = 1,
                countryCode = countryCode
            )
            signupUC.invoke(viewModelScope, signupRequest) { resource ->
                when (resource) {
                    is Resource.Loading -> _viewState.update { SignUpState.Loading }
                    is Resource.Success -> {
                        _viewState.update { SignUpState.Success(resource.model) }
                    }

                    is Resource.Failure -> _viewState.update {
                        SignUpState.Error(
                            resource.exception.message ?: "error in signup"
                        )
                    }
                }
            }
        }
    }
}