package com.solutionplus.altasherat.presentation.ui.fragment.home

import android.os.Bundle
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentVisaApplicationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VisaApplicationsFragment : BaseFragment<FragmentVisaApplicationsBinding>() {
    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
        binding.visaText.text = getString(R.string.visa_applications)
    }
}