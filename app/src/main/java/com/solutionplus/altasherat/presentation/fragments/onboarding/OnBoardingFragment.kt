package com.solutionplus.altasherat.presentation.fragments.onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingBinding
import com.solutionplus.altasherat.presentation.adapters.onboarding.ViewPagerAdapter
import com.solutionplus.altasherat.presentation.fragments.onboarding.screens.OnBoardingFirst
import com.solutionplus.altasherat.presentation.fragments.onboarding.screens.OnBoardingSecond
import com.solutionplus.altasherat.presentation.fragments.onboarding.screens.OnBoardingThird
import kotlinx.coroutines.launch

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    override fun viewInit() {
    }
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val fragmentList = arrayListOf<Fragment>(
            OnBoardingFirst(), OnBoardingSecond(), OnBoardingThird()
        )
        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_homeActivity)
    }

    override fun subscribeToObservables() {
    }


}