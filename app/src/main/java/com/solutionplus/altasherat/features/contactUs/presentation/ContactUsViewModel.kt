package com.solutionplus.altasherat.features.contactUs.presentation

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.contactUs.presentation.ContactUsContract.ContactUsAction
import com.solutionplus.altasherat.features.contactUs.presentation.ContactUsContract.ContactUsEvent
import com.solutionplus.altasherat.features.contactUs.presentation.ContactUsContract.ContactUsState
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesLocalUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserLocalUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesLocalUC,
    private val getUserLocalUC: GetUserLocalUC,

    ) :
    AlTasheratViewModel<ContactUsAction, ContactUsEvent, ContactUsState>(ContactUsState.initial()) {

    init {
        getCountries()
        getUpdatedUserFromLocal()
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is ContactUsAction.GetCountries -> getCountries()
            is ContactUsAction.GetUpdatedUserFromLocal -> getUpdatedUserFromLocal()

        }
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCountriesUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading, exception = null))
                    is Resource.Success -> sendEvent(
                        ContactUsEvent.GetCountries(it.model)
                    )
                }
            }
        }
    }


    private fun getUpdatedUserFromLocal() {
        viewModelScope.launch {
            getUserLocalUC.invoke().collect {
                when (it) {
                    is Resource.Failure -> setState(
                        oldViewState.copy(
                            exception = it.exception
                        )
                    )

                    is Resource.Loading -> setState(oldViewState.copy(isLoading = it.loading, exception = null))
                    is Resource.Success -> {
                        setState(oldViewState.copy(isLoading = false, exception = null))
                        sendEvent(ContactUsEvent.GetUpdatedUserFromLocal(it.model))
                        logger.info("User is ${it.model.country.phoneCode}")
                    }
                }
            }
        }
    }


    override fun clearState() {
        setState(ContactUsState.initial())

    }

    companion object {
        val logger = getClassLogger()
    }

}