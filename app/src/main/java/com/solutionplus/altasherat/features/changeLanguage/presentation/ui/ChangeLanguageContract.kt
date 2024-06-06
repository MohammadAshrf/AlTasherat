package com.solutionplus.altasherat.features.changeLanguage.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ChangeLanguageContract {

    sealed class  ChangeLanguageAction : ViewAction {
        data object GetCountriesFromLocal : ChangeLanguageAction()
        data class SaveSelectedCountry(val country: Country) : ChangeLanguageAction()
        data object GetSelectedCountry : ChangeLanguageAction()
        data class StartLanguageWorker(val language: String) : ChangeLanguageAction()
        data object ContinueToOnBoarding : ChangeLanguageAction()
    }

    sealed class ChangeLanguageEvent : ViewEvent {
        data class CountriesIndex(val countries: List<Country>) : ChangeLanguageEvent()
        data class SaveSelectedCountry(val country: Country) : ChangeLanguageEvent()
        data class GetSelectedCountry(val country: Country): ChangeLanguageEvent()
        data class LanguageWorkerStarted(val language: String) : ChangeLanguageEvent()
        data object NavigateToOnBoarding : ChangeLanguageEvent()
    }

    data class ChangeLanguageState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = ChangeLanguageState(isLoading = false, exception = null, action = null)
        }
    }
}