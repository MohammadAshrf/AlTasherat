package com.solutionplus.altasherat.features.changeLanguage.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ChangeLanguageContract {

    sealed class  ChangeLanguageAction : ViewAction {
        data class StartLanguageWorker(val language: String) : ChangeLanguageAction()
    }

    sealed class ChangeLanguageEvent : ViewEvent {
        data class LanguageWorkerStarted(val language: String) : ChangeLanguageEvent()
    }

    data class ChangeLanguageState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = ChangeLanguageState(isLoading = false, exception = null, action = null)
        }
    }
}