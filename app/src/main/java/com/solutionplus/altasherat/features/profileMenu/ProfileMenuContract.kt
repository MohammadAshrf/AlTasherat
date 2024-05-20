package com.solutionplus.altasherat.features.profileMenu

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface ProfileMenuContract {
    sealed class ProfileMenuAction : ViewAction {
        object SignOut : ProfileMenuAction()
        object GoBack : ProfileMenuAction()
        object ChangeProfile : ProfileMenuAction()
    }

    sealed class ProfileMenuEvent : ViewEvent {
        object NavigateToSignup : ProfileMenuEvent()

        // Navigate to Signup
    }

    data class ProfileMenuState(
        val isLoading: Boolean,
        val exception: LeonException?,
        val action: ViewAction?,
        val isUserLoggedIn: Boolean,
        val doesProfilePictureExist: Boolean,
        val imageUrl: String? = null,
        val fullName: String? = null,
    ) : ViewState {
        companion object {
            fun initial() = ProfileMenuState(
                isLoading = false,
                exception = null,
                action = null,
                isUserLoggedIn = false,
                doesProfilePictureExist = false,
                imageUrl = null,
                fullName = null
            )
        }
    }
}