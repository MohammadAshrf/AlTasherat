package com.solutionplus.altasherat.features.language.presentation.ui

import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.language.domain.worker.LanguageWorker
import com.solutionplus.altasherat.features.language.domain.worker.LanguageWorkerImpl
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageContract.LanguageAction
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageContract.LanguageEvent
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageContract.LanguageState
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val languageWorkerImpl: LanguageWorkerImpl
) :
    AlTasheratViewModel<LanguageAction, LanguageEvent, LanguageState>(LanguageState.initial()) {
    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is LanguageAction.GetCountriesFromLocal -> getCountries()
            is LanguageAction.StartLanguageWorker -> invokeLanguageWorker(action.language)
            is LanguageAction.ContinueToOnBoarding -> navigateToOnBoarding()
        }
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCountriesFromLocalUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> sendEvent(LanguageEvent.CountriesIndex(countries = it.model))
                }
            }
        }
    }

    private fun navigateToOnBoarding() {
        sendEvent(LanguageEvent.NavigateToOnBoarding)
    }

    private fun invokeLanguageWorker(language: String) {
        viewModelScope.launch {
            languageWorkerImpl.updateLanguage(language).collect {
                when (it.state) {
                    WorkInfo.State.ENQUEUED -> {}
                    WorkInfo.State.RUNNING -> {}
                    WorkInfo.State.SUCCEEDED -> {
                        val succeededMessage = it.outputData.getString(LanguageWorker.KEY_SUCCESS)
                        sendEvent(LanguageEvent.LanguageWorkerStarted(language))
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
        setState(LanguageState.initial())
    }
}
