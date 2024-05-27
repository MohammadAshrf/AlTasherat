package com.solutionplus.altasherat.features.onboarding.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface OnBoardingContract {

    sealed class OnBoardingAction : ViewAction {
        data object SetOnBoardingShown : OnBoardingAction()
    }

    sealed class OnBoardingEvent : ViewEvent {
        data object NavigateToHome : OnBoardingEvent()
    }

    data class OnBoardingState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = OnBoardingState(isLoading = false, exception = null, action = null)
        }
    }
}