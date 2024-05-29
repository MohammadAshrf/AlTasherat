package com.solutionplus.altasherat.features.deleteaccount.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.presentation.ui.fragment.ChangePasswordContract
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest
import com.solutionplus.altasherat.features.deleteaccount.domain.usecase.DeleteAccountUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteAccountVM @Inject constructor(
    private val deleteAccountUC:DeleteAccountUC
) : AlTasheratViewModel<DeleteAccountContract.DeleteAccountActions, DeleteAccountContract.DeleteAccountEvents, DeleteAccountContract.DeleteAccountState>(initialState = DeleteAccountContract.DeleteAccountState.initial())  {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is DeleteAccountContract.DeleteAccountActions.DeleteAccount -> handleDeleteAccount(action.password)
        }
    }

    override fun clearState() {
        setState(DeleteAccountContract.DeleteAccountState.initial())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleDeleteAccount(password:String) {
        val deleteAccountRequest = DeleteAccountRequest(password =  password)
        deleteAccountUC.invoke(viewModelScope,deleteAccountRequest){ resource ->
            when (resource) {
                is Resource.Failure -> setState(oldViewState.copy(isLoading = false, exception = resource.exception))
                is Resource.Loading -> setState(oldViewState.copy(isLoading = resource.loading))
                is Resource.Success -> {
                    setState(oldViewState.copy(isLoading = false, exception = null))
                    sendEvent(DeleteAccountContract.DeleteAccountEvents.DeleteAccountSuccess("Your Account is Deleted successfully"))
                }
            }
        }
    }
}