package com.solutionplus.altasherat.features.deleteaccount.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface DeleteAccountContract {
    //Action
    sealed class DeleteAccountActions: ViewAction {
        data class DeleteAccount (val password: String) : DeleteAccountActions()
    }
    //Event
    sealed class DeleteAccountEvents: ViewEvent {
        data class DeleteAccountSuccess(val message: String) : DeleteAccountEvents()
    }

    //State
    data class DeleteAccountState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = DeleteAccountState(isLoading = false, exception = null, action = null)
        }
    }

}