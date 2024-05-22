package com.solutionplus.altasherat.feature.splash

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface SplashContract {

    sealed class SplashAction : ViewAction {
        data object IsOnBoardingShown : SplashAction()
    }

    sealed class SplashEvent : ViewEvent {
//        data class CountriesIndex(val countries: List<Country>) : SplashEvent()
        data object NavigateToOnBoarding : SplashEvent()
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