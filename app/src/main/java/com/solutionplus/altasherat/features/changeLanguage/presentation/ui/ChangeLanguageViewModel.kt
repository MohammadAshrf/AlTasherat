package com.solutionplus.altasherat.features.changeLanguage.presentation.ui

import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.changeLanguage.presentation.ui.ChangeLanguageContract.ChangeLanguageAction
import com.solutionplus.altasherat.features.changeLanguage.presentation.ui.ChangeLanguageContract.ChangeLanguageEvent
import com.solutionplus.altasherat.features.changeLanguage.presentation.ui.ChangeLanguageContract.ChangeLanguageState
import com.solutionplus.altasherat.features.services.language.domain.worker.LanguageWorker
import com.solutionplus.altasherat.features.services.language.domain.worker.LanguageWorkerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(
    private val languageWorkerImpl: LanguageWorkerImpl

) :
    AlTasheratViewModel<ChangeLanguageAction, ChangeLanguageEvent, ChangeLanguageState>(
        ChangeLanguageState.initial()
    ) {
    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is ChangeLanguageAction.StartLanguageWorker -> invokeLanguageWorker(action.language)
        }
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