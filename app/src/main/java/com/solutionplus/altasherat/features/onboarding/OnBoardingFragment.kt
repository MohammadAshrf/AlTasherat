package com.solutionplus.altasherat.features.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingBinding
import com.solutionplus.altasherat.features.onboarding.screens.OnBoardingFirst
import com.solutionplus.altasherat.features.onboarding.screens.OnBoardingSecond
import com.solutionplus.altasherat.features.onboarding.screens.OnBoardingThird
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

        val callback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateRightIconVisibility(adapter.itemCount - 3)
            }
        }
        binding.viewPager.registerOnPageChangeCallback(callback)

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

    private fun updateRightIconVisibility(lastIndex: Int) {
        binding.icRightRounded.visibility =
            if (binding.viewPager.currentItem == lastIndex) View.INVISIBLE else View.VISIBLE

        binding.backButton.visibility =
            if (binding.viewPager.currentItem == lastIndex) View.INVISIBLE else View.VISIBLE
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_homeActivity)
    }

    override fun subscribeToObservables() {
    }


}