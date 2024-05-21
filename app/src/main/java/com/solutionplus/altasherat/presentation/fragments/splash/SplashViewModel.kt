package com.solutionplus.altasherat.presentation.fragments.splash

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.feature.services.country.domain.interactor.GetCountriesUC
import com.solutionplus.altasherat.presentation.fragments.splash.SplashContract.SplashAction
import com.solutionplus.altasherat.presentation.fragments.splash.SplashContract.SplashEvent
import com.solutionplus.altasherat.presentation.fragments.splash.SplashContract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getCountriesUC: GetCountriesUC) :
    AlTasheratViewModel<SplashAction, SplashEvent, SplashState>(SplashState.initial()) {

    private fun fetchCountries() {
        viewModelScope.launch {
            getCountriesUC.emitCountries().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> sendEvent(
                        if (it.model)
                            SplashEvent.NavigateToHome else
                            SplashEvent.NavigateToOnBoarding
                    )
                }
            }
        }
    }

    override fun invokeAction(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            SplashAction.FetchCountries -> fetchCountries()
        }
    }

    override fun clearState() {
        setState(SplashState.initial())
    }
}
