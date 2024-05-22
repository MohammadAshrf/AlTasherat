package com.solutionplus.altasherat.features.profileMenu

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.profileMenu.domain.model.User

interface ProfileMenuContract {
    sealed class ProfileMenuAction : ViewAction {
        object SignOut : ProfileMenuAction()
        object ChangeProfile : ProfileMenuAction()
        object CheckUserLogin : ProfileMenuAction()
    }

    sealed class ProfileMenuEvent : ViewEvent {
        data class IsUserLoggedIn(val user: User) : ProfileMenuEvent()
    }

    data class ProfileMenuState(
        val isLoading: Boolean,
        val exception: LeonException?,
        val action: ViewAction?,
        val isUserLoggedIn: Boolean = false,
        ) : ViewState {
        companion object {
            fun initial() = ProfileMenuState(
                isLoading = false,
                exception = null,
                action = null,
                isUserLoggedIn = false,
                )
        }
    }
}