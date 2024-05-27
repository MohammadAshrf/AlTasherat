package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface PersonalInfoContract {

    sealed class PersonalInfoAction : ViewAction {

    }

    sealed class PersonalInfoEvent : ViewEvent {
    }

    data class PersonalInfoState(
        val isLoading: Boolean, val exception: LeonException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = PersonalInfoState(isLoading = false, exception = null, action = null)
        }
    }
}