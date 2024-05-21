package com.solutionplus.altasherat.features.signup.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
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
) : AlTasheratViewModel<SignUpContract.SignupActions, SignUpContract.SignupEvent, SignUpContract.SignUpState>(initialState = SignUpContract.SignUpState.initial()) {
    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is SignUpContract.SignupActions.Signup -> signUp(
                action.firstName,
                action.lastName,
                action.email,
                action.phoneNumber,
                action.countryCode,
                action.password
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

            setState(oldViewState.copy(isLoading = true))

            signupUC.invoke(viewModelScope, signupRequest) { resource ->
                when (resource) {
                    is Resource.Failure -> setState(oldViewState.copy(isLoading = false, exception = resource.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = resource.loading))
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