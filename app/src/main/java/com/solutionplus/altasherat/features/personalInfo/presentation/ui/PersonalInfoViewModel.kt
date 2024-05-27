package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(

) :
    AlTasheratViewModel<PersonalInfoAction, PersonalInfoEvent, PersonalInfoState>(PersonalInfoState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))

    }

    override fun clearState() {
        setState(PersonalInfoState.initial())
    }
}
