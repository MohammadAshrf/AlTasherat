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

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
            when (action) {
                is ChangePasswordContract.ChangePasswordActions.ChangePassword -> changePassword(
                    action.oldPassword,
                    action.newPassword,
                    action.retypeNewPassword,
                )
            }
    }

    private fun changePassword(oldPassword:String, newPassword:String, newPasswordConfirmation:String){
        viewModelScope.launch {
            val changePasswordRequest = ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = newPassword,
                newPasswordConfirmation = newPasswordConfirmation,
                token = changePasswordUC.token()
            )

            changePasswordUC.invoke(viewModelScope,changePasswordRequest){ resource ->
                when (resource) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = resource.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = resource.loading))
                    is Resource.Success -> {
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