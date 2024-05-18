package com.solutionplus.altasherat.common.presentation.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel <State, Intent> : ViewModel() {
    protected val _viewState = MutableStateFlow<State>(initialState)
    val viewState = _viewState.asStateFlow()

    abstract val initialState: State

  abstract  fun handleIntent(intent: Intent)
}