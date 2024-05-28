package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>() {

    private val personalInfoVM: PersonalInfoViewModel by viewModels()
    override fun viewInit() {
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun subscribeToObservables() {
        handleEvents()
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(personalInfoVM.singleEvent) {
        }
    }
}