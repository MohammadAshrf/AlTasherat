package com.solutionplus.altasherat.features.profileMenu

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginContract
import com.solutionplus.altasherat.features.profileMenu.domain.model.User

interface ProfileMenuContract {
    sealed class ProfileMenuAction : ViewAction {
        object Logout : ProfileMenuAction()

        object GetUser : ProfileMenuAction()

        object IsUserLoggedIn : ProfileMenuAction()
    }

    sealed class ProfileMenuEvent : ViewEvent {
        data class GetUser(val user: User) : ProfileMenuEvent()
        data class IsUserLoggedIn(val isUserLoggedIn: Boolean) : ProfileMenuEvent()
        data class LogoutSuccess(val message: String) : ProfileMenuEvent()
    }

    data class ProfileMenuState(
        val isLoading: Boolean,
        val exception: LeonException?,
        val action: ViewAction?,
        ) : ViewState {
        companion object {
            fun initial() = ProfileMenuState(
                isLoading = false,
                exception = null,
                action = null,
                )
        }
    }
}