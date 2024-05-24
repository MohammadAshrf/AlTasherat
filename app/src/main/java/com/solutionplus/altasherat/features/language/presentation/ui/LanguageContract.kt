package com.solutionplus.altasherat.features.language.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface LanguageContract {

    sealed class LanguageAction : ViewAction {
        data object GetArCountries : LanguageAction()
        data object GetEnCountries : LanguageAction()
        data object StartLanguageWorker : LanguageAction()
        data object StartEnLanguageWorker : LanguageAction()
        data object ContinueToOnBoarding : LanguageAction()
    }

    sealed class LanguageEvent : ViewEvent {
        data class CountriesIndex(val countries: List<Country>) : LanguageEvent()
        data object LanguageWorkerStarted : LanguageEvent()
        data object NavigateToOnBoarding : LanguageEvent()
    }

    data class LanguageState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = LanguageState(isLoading = false, exception = null, action = null)
        }
    }
}