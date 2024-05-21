package com.solutionplus.altasherat.features.changepassword.presentation.ui.fragment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.usecase.ChangePasswordUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUC: ChangePasswordUC
): AlTasheratViewModel<ChangePasswordContract.ChangePasswordActions, ChangePasswordContract.ChangePasswordEvents, ChangePasswordContract.ChangePasswordState>(initialState = ChangePasswordContract.ChangePasswordState.initial())  {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActionTrigger(action: ViewAction?) {
            when (action) {
                is ChangePasswordContract.ChangePasswordActions.ChangePassword -> changePassword(
                    action.oldPassword,
                    action.newPassword,
                    action.retypeNewPassword,
                )
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun changePassword(oldPassword:String, newPassword:String, newPasswordConfirmation:String){
        viewModelScope.launch {
            val changePasswordRequest = ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = newPassword,
                newPasswordConfirmation = newPasswordConfirmation,
                token = changePasswordUC.token()
            )

            setState(oldViewState.copy(isLoading = true))
            changePasswordUC.invoke(viewModelScope,changePasswordRequest){ resource ->
//                when (resource){
//                    is Resource.Loading -> setState(ChangePasswordState.Loading)
//                    is Resource.Success -> {
//                        setState(ChangePasswordContract.ChangePasswordState.Success(null))
//                        sendEvent(ChangePasswordEvents.ChangePasswordSuccess("Password changed successfully"))
//                    }
//                    is Resource.Failure -> {
//                        setState(ChangePasswordState.Error(resource.exception.message ?: "Error changing password"))
//                    }
//
//                }

                when (resource) {
                    is Resource.Failure -> setState(oldViewState.copy(isLoading = false, exception = resource.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = resource.loading))
                    is Resource.Success -> {
                        setState(oldViewState.copy(isLoading = false, exception = null))
                        sendEvent(ChangePasswordContract.ChangePasswordEvents.ChangePasswordSuccess("Password changed successfully"))
                    }
                }
            }
        }
    }
    override fun clearState() {
        setState(ChangePasswordContract.ChangePasswordState.initial())
    }
}