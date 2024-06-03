package com.solutionplus.altasherat.features.onboarding.presentation.ui.screens

import android.os.Bundle
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingFirstBinding

class OnBoardingFirst : BaseFragment<FragmentOnBoardingFirstBinding>() {
    override fun viewInit() {
        binding.cardView.helloOnboarding.text = getString(R.string.hello_onboarding)
        binding.cardView.onboardingDescription.text = getString(R.string.first_onboarding)
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun subscribeToObservables() {
    }
}