package com.solutionplus.altasherat.feature.onboarding.screens

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingThirdBinding

class OnBoardingThird() : BaseFragment<FragmentOnBoardingThirdBinding>() {

    override fun viewInit() {
        binding
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {

        binding.cardView.helloOnboarding.text = getString(R.string.hello_onboarding)
        binding.cardView.onboardingDescription.text = getString(R.string.third_onboarding)
//        binding.cardView.nextButton.setOnClickListener {
//            lifecycleScope.launch {
//                findNavController().navigate(R.id.action_onBoardingFragment_to_homeActivity)
//                activity?.finish()
//            }
//        }

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
//        binding.icRightRounded.setOnClickListener {
//            viewPager?.setCurrentItem(viewPager.currentItem - 1, true)
//        }
    }

    override fun subscribeToObservables() {
    }

}