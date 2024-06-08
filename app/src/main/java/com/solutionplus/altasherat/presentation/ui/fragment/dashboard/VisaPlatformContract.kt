package com.solutionplus.altasherat.presentation.ui.fragment.dashboard

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface VisaPlatformContract {

    sealed class  VisaPlatformAction : ViewAction {
        object IsUserLoggedIn : VisaPlatformAction()
    }

    sealed class  VisaPlatformEvent : ViewEvent {
        data class IsUserLoggedIn(val isUserLoggedIn: Boolean) : VisaPlatformEvent()
    }

    data class VisaPlatformState(
        val isLoading: Boolean,
        val exception: LeonException?,
        val action: ViewAction?,
    ) : ViewState {
        companion object {
            fun initial() = VisaPlatformState(
                isLoading = false,
                exception = null,
                action = null,
            )
        }
    }
}