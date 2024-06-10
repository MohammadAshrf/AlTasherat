package com.solutionplus.altasherat.features.deleteaccount.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest
import com.solutionplus.altasherat.features.deleteaccount.domain.usecase.DeleteAccountUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteAccountVM @Inject constructor(
    private val deleteAccountUC: DeleteAccountUC
) : AlTasheratViewModel<DeleteAccountContract.DeleteAccountActions, DeleteAccountContract.DeleteAccountEvents, DeleteAccountContract.DeleteAccountState>(
    initialState = DeleteAccountContract.DeleteAccountState.initial()
) {

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is DeleteAccountContract.DeleteAccountActions.DeleteAccount -> handleDeleteAccount(
                action.password
            )
        }
    }

    override fun clearState() {
        setState(DeleteAccountContract.DeleteAccountState.initial())
    }

    private fun handleDeleteAccount(password: String) {
        val deleteAccountRequest = DeleteAccountRequest(password = password)
        deleteAccountUC.invoke(viewModelScope, deleteAccountRequest) { resource ->
            when (resource) {
                is Resource.Failure -> {
                    setState(oldViewState.copy(exception = resource.exception))
                }

                is Resource.Loading -> setState(oldViewState.copy(isLoading = resource.loading, exception = null))
                is Resource.Success -> {
                    sendEvent(DeleteAccountContract.DeleteAccountEvents.DeleteAccountSuccess("Your Account is Deleted successfully"))
                }
            }
        }
    }
}