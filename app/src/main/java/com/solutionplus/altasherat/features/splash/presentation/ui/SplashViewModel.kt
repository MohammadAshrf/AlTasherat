package com.solutionplus.altasherat.features.splash.presentation.ui

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.onboarding.domain.interactor.IsOnboardingShownUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.HasCountriesUC
import com.solutionplus.altasherat.features.splash.presentation.ui.SplashContract.SplashAction
import com.solutionplus.altasherat.features.splash.presentation.ui.SplashContract.SplashEvent
import com.solutionplus.altasherat.features.splash.presentation.ui.SplashContract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesUC,
    private val hasCountriesUC: HasCountriesUC,
    private val isOnboardingShownUC: IsOnboardingShownUC,
) :
    AlTasheratViewModel<SplashAction, SplashEvent, SplashState>(SplashState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is SplashAction.IsOnBoardingShown -> isOnboardingShown()
        }
    }

    private fun isOnboardingShown() {
        viewModelScope.launch {
            isOnboardingShownUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading, exception = null))
                    is Resource.Success -> if (it.model) {
                        sendEvent(SplashEvent.NavigateToHome)
                    } else {
                        fetchCountriesAndNavigateToLanguage()
                    }
                }
            }
        }
    }

    private fun fetchCountriesAndNavigateToLanguage() {
        viewModelScope.launch {
            hasCountriesUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading, exception = null))
                    is Resource.Success -> if (it.model) {
                        sendEvent(SplashEvent.FetchCountriesFromLocalAndNavigateToLanguage)
                    } else {
                        fetchCountryFromRemote()
                    }
                }
            }
        }
    }

    private fun fetchCountryFromRemote() {
        viewModelScope.launch {
            getCountriesUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading, exception = null))
                    is Resource.Success -> sendEvent(SplashEvent.FetchCountriesFromRemote)
                }
            }
        }
    }

    override fun clearState() {
        setState(SplashState.initial())
    }
}