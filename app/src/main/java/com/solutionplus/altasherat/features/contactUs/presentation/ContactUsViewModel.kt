package com.solutionplus.altasherat.features.contactUs.presentation

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import com.solutionplus.altasherat.features.contactUs.presentation.ContactUsContract.ContactUsAction
import com.solutionplus.altasherat.features.contactUs.presentation.ContactUsContract.ContactUsState
import com.solutionplus.altasherat.features.contactUs.presentation.ContactUsContract.ContactUsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesFromLocalUC
) : AlTasheratViewModel<ContactUsAction,ContactUsEvent,ContactUsState>(ContactUsState.initial()) {

    init {
        getCountries()
    }
    private fun getCountries() {
        viewModelScope.launch {
            getCountriesUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading))
                    is Resource.Success -> sendEvent(
                        ContactUsEvent.CountriesIndex(
                            countries = it.model
                        )
                    )
                }
            }
        }
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is ContactUsAction.GetCountries -> getCountries()
        }
    }

    override fun clearState() {
        setState(ContactUsState.initial())

    }

}