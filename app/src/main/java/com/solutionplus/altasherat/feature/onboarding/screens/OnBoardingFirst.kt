package com.solutionplus.altasherat.feature.onboarding.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingFirstBinding

class OnBoardingFirst : BaseFragment<FragmentOnBoardingFirstBinding>() {
    override fun viewInit() {
    }
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        binding.cardView.helloOnboarding.text = getString(R.string.hello_onboarding)
        binding.cardView.onboardingDescription.text = getString(R.string.first_onboarding)
//        binding.cardView.nextButton.setOnClickListener {
//                viewPager?.currentItem = 1
//        }
    }

    override fun subscribeToObservables() {
    }
}