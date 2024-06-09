package com.solutionplus.altasherat.features.profileMenu.Presentation

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.profileMenu.Presentation.ProfileMenuContract.ProfileMenuAction
import com.solutionplus.altasherat.features.profileMenu.Presentation.ProfileMenuContract.ProfileMenuState
import com.solutionplus.altasherat.features.profileMenu.Presentation.ProfileMenuContract.ProfileMenuEvent
import com.solutionplus.altasherat.features.profileMenu.domain.usecase.CheckUserStateUC
import com.solutionplus.altasherat.features.profileMenu.domain.usecase.GetUserUC
import com.solutionplus.altasherat.features.profileMenu.domain.usecase.LogoutUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMenuViewModel @Inject constructor(
    private val getUserUC: GetUserUC,
    private val checkUserStateUC: CheckUserStateUC,
    private val logoutUC: LogoutUC
) : AlTasheratViewModel<ProfileMenuAction, ProfileMenuEvent, ProfileMenuState>(ProfileMenuState.initial()) {

    override fun clearState() {
        setState(ProfileMenuState.initial())
    }



    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is ProfileMenuAction.Logout -> handleLogout()
            is ProfileMenuAction.GetUser -> getUser()
            is ProfileMenuAction.IsUserLoggedIn -> checkUserLogin()
        }
    }

    private fun handleLogout() {
        logoutUC.invoke(viewModelScope,null) {
            sendEvent(ProfileMenuEvent.LogoutSuccess("Logout successful"))
        }
    }

    private fun checkUserLogin() {
        viewModelScope.launch {
            checkUserStateUC.invoke(viewModelScope, null) {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> {
                        sendEvent(ProfileMenuEvent.IsUserLoggedIn(it.model))
                    }
                }
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUC.invoke(viewModelScope, null) {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> {
                        sendEvent(ProfileMenuEvent.GetUser(it.model))
                    }
                }
            }
        }
    }

    companion object{
        val logger = getClassLogger()
    }
}