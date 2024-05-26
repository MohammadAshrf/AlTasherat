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
    private val isOnboardingShownUC: IsOnboardingShownUC,
    private val hasCountriesUC: HasCountriesUC
) :
    AlTasheratViewModel<SplashAction, SplashEvent, SplashState>(SplashState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            SplashAction.IsOnBoardingShown -> isOnboardingShown()
            SplashAction.HasCountries -> hasCountries()
        }
    }

    private fun hasCountries() {
        viewModelScope.launch {
            hasCountriesUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> if (it.model) {
                        processIntent(SplashAction.IsOnBoardingShown)
                    } else {
                        fetchCountriesAndNavigateToLanguage()
                    }
                }
            }
        }
    }

    private fun isOnboardingShown() {
        viewModelScope.launch {
            isOnboardingShownUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> if (it.model) {
                        sendEvent(SplashEvent.NavigateToHome)
                        } else {
                        sendEvent(SplashEvent.NavigateToOnBoarding)
                    }
                }
            }
        }
    }

    private fun fetchCountriesAndNavigateToLanguage() {
        viewModelScope.launch {
            getCountriesUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> sendEvent(SplashEvent.NavigateToLanguage)
                }
            }
        }
    }

    override fun clearState() {
        setState(SplashState.initial())
    }
}
