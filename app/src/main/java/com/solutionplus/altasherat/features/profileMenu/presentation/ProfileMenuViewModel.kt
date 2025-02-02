package com.solutionplus.altasherat.features.profileMenu.presentation

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract
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
) : AlTasheratViewModel<ProfileMenuContract.ProfileMenuAction, ProfileMenuContract.ProfileMenuEvent, ProfileMenuContract.ProfileMenuState>(
    ProfileMenuContract.ProfileMenuState.initial()
) {

    override fun clearState() {
        setState(ProfileMenuContract.ProfileMenuState.initial())
    }


    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is ProfileMenuContract.ProfileMenuAction.Logout -> handleLogout()
            is ProfileMenuContract.ProfileMenuAction.GetUser -> getUser()
            is ProfileMenuContract.ProfileMenuAction.IsUserLoggedIn -> checkUserLogin()
        }
    }

    private fun handleLogout() {
        logoutUC.invoke(viewModelScope, null) {
            sendEvent(ProfileMenuContract.ProfileMenuEvent.LogoutSuccess("Logout Successfully!"))
        }
    }

    private fun checkUserLogin() {
        viewModelScope.launch {
            checkUserStateUC.invoke(viewModelScope, null) {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(
                        oldViewState.copy(
                            isLoading = false,
                            exception = null
                        )
                    )

                    is Resource.Success -> {
                        sendEvent(ProfileMenuContract.ProfileMenuEvent.IsUserLoggedIn(it.model))
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
                    is Resource.Loading -> setState(
                        oldViewState.copy(
                            isLoading = false,
                            exception = null
                        )
                    )

                    is Resource.Success -> {
                        sendEvent(ProfileMenuContract.ProfileMenuEvent.GetUser(it.model))
                    }
                }
            }
        }
    }
}