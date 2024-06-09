package com.solutionplus.altasherat.presentation.ui.fragment.dashboard

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract
import com.solutionplus.altasherat.features.profileMenu.domain.usecase.CheckUserStateUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisaPlatformVM @Inject constructor(
    private val checkUserStateUC: CheckUserStateUC
) : AlTasheratViewModel<VisaPlatformContract.VisaPlatformAction, VisaPlatformContract.VisaPlatformEvent, VisaPlatformContract.VisaPlatformState>(
    VisaPlatformContract.VisaPlatformState.initial()){
    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is ProfileMenuContract.ProfileMenuAction.IsUserLoggedIn -> checkUserLogin()
        }
    }

    override fun clearState() {
        setState(VisaPlatformContract.VisaPlatformState.initial())
    }

    private fun checkUserLogin() {
        viewModelScope.launch {
            checkUserStateUC.invoke(viewModelScope, null) {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> {
                        sendEvent(VisaPlatformContract.VisaPlatformEvent.IsUserLoggedIn(it.model))
                    }
                }
            }
        }
    }
}