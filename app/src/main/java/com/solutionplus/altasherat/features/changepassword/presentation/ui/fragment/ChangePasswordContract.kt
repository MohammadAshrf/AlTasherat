package com.solutionplus.altasherat.features.changepassword.presentation.ui.fragment

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface ChangePasswordContract {
    //Action
    sealed class ChangePasswordActions:ViewAction {
        data class ChangePassword (val oldPassword: String, val newPassword: String, val retypeNewPassword: String) : ChangePasswordActions()
    }
    //Event
    sealed class ChangePasswordEvents: ViewEvent {
        data class ChangePasswordSuccess(val message: String) : ChangePasswordEvents()
       data class ChangePasswordError(val exception: LeonException) : ChangePasswordEvents()
    }

    //State
    data class ChangePasswordState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = ChangePasswordState(isLoading = false, exception = null, action = null)
        }
    }

}