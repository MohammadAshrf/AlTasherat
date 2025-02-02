package com.solutionplus.altasherat.features.splash.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface SplashContract {

    sealed class SplashAction : ViewAction {
        data object IsOnBoardingShown : SplashAction()
    }

    sealed class SplashEvent : ViewEvent {
        data object FetchCountriesFromLocalAndNavigateToLanguage : SplashEvent()
        data object FetchCountriesFromRemote : SplashEvent()
        data object NavigateToHome : SplashEvent()
    }

    data class SplashState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = SplashState(isLoading = false, exception = null, action = null)
        }
    }
}