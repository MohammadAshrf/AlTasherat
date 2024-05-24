package com.solutionplus.altasherat.features.splash

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.IsOnboardingShownUC
import com.solutionplus.altasherat.features.splash.SplashContract.SplashAction
import com.solutionplus.altasherat.features.splash.SplashContract.SplashEvent
import com.solutionplus.altasherat.features.splash.SplashContract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesUC,
    private val isOnboardingShownUC: IsOnboardingShownUC
) :
    AlTasheratViewModel<SplashAction, SplashEvent, SplashState>(SplashState.initial()) {

    private fun fetchAndSaveCountries() {
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

    private fun isOnboardingShown() {
        viewModelScope.launch {
            isOnboardingShownUC.emitIsOnboardingShown().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> if (it.model) {
                        sendEvent(SplashEvent.NavigateToHome)
                    } else {
                        fetchAndSaveCountries()
                    }
                }
            }
        }
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            SplashAction.IsOnBoardingShown -> isOnboardingShown()
        }
    }

    override fun clearState() {
        setState(SplashState.initial())
    }
}
