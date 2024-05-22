package com.solutionplus.altasherat.feature.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingBinding
import com.solutionplus.altasherat.feature.onboarding.screens.OnBoardingFirst
import com.solutionplus.altasherat.feature.onboarding.screens.OnBoardingSecond
import com.solutionplus.altasherat.feature.onboarding.screens.OnBoardingThird
import com.solutionplus.altasherat.presentation.adapters.onboarding.ViewPagerAdapter

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    override fun viewInit() {
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val fragmentList = arrayListOf<Fragment>(
            OnBoardingFirst(), OnBoardingSecond(), OnBoardingThird()
        )
        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

//        binding.icRightRounded.visibility =
//            if (binding.viewPager.currentItem == adapter.itemCount - 1) View.INVISIBLE else View.VISIBLE

//        if (fragmentList.size == 1) {
//            binding.icRightRounded.visibility = View.INVISIBLE
//        }

        binding.icRightRounded.setOnClickListener {
            if (binding.viewPager.currentItem + 1 < adapter.itemCount) {
                binding.viewPager.currentItem -= 1
            } else {
                binding.viewPager.currentItem -= 1
            }
        }

        binding.nextButton.setOnClickListener {
            if (binding.viewPager.currentItem + 1 < adapter.itemCount) {
                binding.viewPager.currentItem += 1
            } else {
                navigateToHome()
            }
        }

        binding.dotsIndicator.attachTo(binding.viewPager)
    }


    private fun navigateToHome() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_homeActivity)
    }

    override fun subscribeToObservables() {
    }


}