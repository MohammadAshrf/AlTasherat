package com.solutionplus.altasherat.features.onboarding.presentation.ui.screens

import android.os.Bundle
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingSecondBinding

class OnBoardingSecond : BaseFragment<FragmentOnBoardingSecondBinding>() {
    override fun viewInit() {
        binding.cardView.helloOnboarding.text = getString(R.string.hello_onboarding)
        binding.cardView.onboardingDescription.text = getString(R.string.second_onboarding)
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun subscribeToObservables() {
    }
}