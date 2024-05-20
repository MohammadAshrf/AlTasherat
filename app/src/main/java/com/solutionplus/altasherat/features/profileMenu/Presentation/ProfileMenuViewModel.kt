package com.solutionplus.altasherat.features.profileMenu.Presentation

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract.ProfileMenuAction
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract.ProfileMenuState
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract.ProfileMenuEvent
import com.solutionplus.altasherat.features.profileMenu.domain.usecase.CheckUserLoginUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMenuViewModel @Inject constructor(
    private val checkUserLoginUC: CheckUserLoginUC
) : AlTasheratViewModel<ProfileMenuAction, ProfileMenuEvent, ProfileMenuState>(ProfileMenuState.initial()) {

    override fun clearState() {
        setState(ProfileMenuState.initial())
    }

    init {
        checkUserLogin()
    }

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is ProfileMenuAction.SignOut -> handleSignOut()
            is ProfileMenuAction.GoBack -> handleGoBack()
            is ProfileMenuAction.ChangeProfile -> handleChangeProfile()
        }
    }

    private fun handleSignOut() {
        // Handle sign out
    }

    private fun handleGoBack() {
        // Handle go back
    }

    private fun handleChangeProfile() {
        // Handle change profile
    }

    private fun checkUserLogin() {
        viewModelScope.launch {
            val user = checkUserLoginUC.execute(null)
            val newState = if (user != null) {
                oldViewState.copy(
                    isUserLoggedIn = true,
                    doesProfilePictureExist = user.imageUrl != null,
                    imageUrl = user.imageUrl,
                    fullName = user.fullName
                    )
            } else {
                oldViewState.copy(
                    isUserLoggedIn = false,
                    doesProfilePictureExist = false
                )
            }
            setState(newState)
        }
    }

}