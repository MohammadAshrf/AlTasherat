package com.solutionplus.altasherat.features.onboarding.presentation.ui

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.onboarding.domain.interactor.SetOnboardingShownUC
import com.solutionplus.altasherat.features.onboarding.presentation.ui.OnBoardingContract.OnBoardingAction
import com.solutionplus.altasherat.features.onboarding.presentation.ui.OnBoardingContract.OnBoardingEvent
import com.solutionplus.altasherat.features.onboarding.presentation.ui.OnBoardingContract.OnBoardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val setOnboardingShownUC: SetOnboardingShownUC
) :
    AlTasheratViewModel<OnBoardingAction, OnBoardingEvent, OnBoardingState>(OnBoardingState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is OnBoardingAction.SetOnBoardingShown -> setOnboardingShown()
        }
    }

    private fun setOnboardingShown() {
        viewModelScope.launch {
            setOnboardingShownUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> sendEvent(OnBoardingEvent.NavigateToHome)
                }
            }
        }
    }

    override fun clearState() {
        setState(OnBoardingState.initial())
    }
}