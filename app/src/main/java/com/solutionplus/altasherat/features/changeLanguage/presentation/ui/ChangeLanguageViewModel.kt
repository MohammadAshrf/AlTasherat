package com.solutionplus.altasherat.features.changeLanguage.presentation.ui

import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.services.language.domain.interactor.GetSelectedCountryUC
import com.solutionplus.altasherat.features.services.language.domain.interactor.SaveSelectedCountryUC
import com.solutionplus.altasherat.features.services.language.domain.worker.LanguageWorker
import com.solutionplus.altasherat.features.services.language.domain.worker.LanguageWorkerImpl
import com.solutionplus.altasherat.features.changeLanguage.presentation.ui.ChangeLanguageContract.ChangeLanguageAction
import com.solutionplus.altasherat.features.changeLanguage.presentation.ui.ChangeLanguageContract.ChangeLanguageEvent
import com.solutionplus.altasherat.features.changeLanguage.presentation.ui.ChangeLanguageContract.ChangeLanguageState
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val getSelectedCountryUC: GetSelectedCountryUC,
    private val saveSelectedCountryUC: SaveSelectedCountryUC,
    private val languageWorkerImpl: LanguageWorkerImpl

) :
    AlTasheratViewModel<ChangeLanguageAction, ChangeLanguageEvent, ChangeLanguageState>(ChangeLanguageState.initial()) {
    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is ChangeLanguageAction.GetCountriesFromLocal -> getCountriesFromLocal()
            is ChangeLanguageAction.SaveSelectedCountry -> saveSelectedCountry(action.country)
            is ChangeLanguageAction.GetSelectedCountry -> getSelectedCountry()
            is ChangeLanguageAction.StartLanguageWorker -> invokeLanguageWorker(action.language)
            is ChangeLanguageAction.ContinueToOnBoarding -> navigateToOnBoarding()
        }
    }

    private fun saveSelectedCountry(country: Country) {
        viewModelScope.launch {
            saveSelectedCountryUC.invoke(country).collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading, exception = null))
                    is Resource.Success -> sendEvent(ChangeLanguageEvent.SaveSelectedCountry(country))
                }
            }
        }
    }

    private fun getSelectedCountry() {
        viewModelScope.launch {
            getSelectedCountryUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading, exception = null))
                    is Resource.Success -> sendEvent(ChangeLanguageEvent.GetSelectedCountry(it.model))
                }
            }
        }
    }

    private fun getCountriesFromLocal() {
        viewModelScope.launch {
            getCountriesFromLocalUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading, exception = null))
                    is Resource.Success -> sendEvent(ChangeLanguageEvent.CountriesIndex(countries = it.model))
                }
            }
        }
    }

    private fun navigateToOnBoarding() {
        sendEvent(ChangeLanguageEvent.NavigateToOnBoarding)
    }

    private fun invokeLanguageWorker(language: String) {
        viewModelScope.launch {
            languageWorkerImpl.updateLanguage(language).collect {
                when (it.state) {
                    WorkInfo.State.ENQUEUED -> {}
                    WorkInfo.State.RUNNING -> {}
                    WorkInfo.State.SUCCEEDED -> {
                        val succeededMessage = it.outputData.getString(LanguageWorker.KEY_SUCCESS)
                        sendEvent(ChangeLanguageEvent.LanguageWorkerStarted(language))
                        succeededMessage ?: return@collect
                    }

                    WorkInfo.State.FAILED -> {
                        val errorMessage = it.outputData.getString(LanguageWorker.KEY_ERROR)
                        errorMessage ?: return@collect
                    }

                    WorkInfo.State.BLOCKED -> {}
                    WorkInfo.State.CANCELLED -> {}
                }
            }
        }
    }

    override fun clearState() {
        setState(ChangeLanguageState.initial())
    }
}