package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>() {

    private val personalInfoVM: PersonalInfoViewModel by viewModels()
    override fun viewInit() {
        binding.moreButton?.setOnClickListener {
            findNavController().navigate(R.id.action_personalInfoFragment_to_gotoDeleteAccountFragment)
        }
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