package com.solutionplus.altasherat.features.onboarding.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingBinding
import com.solutionplus.altasherat.features.onboarding.presentation.ui.screens.OnBoardingFirst
import com.solutionplus.altasherat.features.onboarding.presentation.ui.screens.OnBoardingSecond
import com.solutionplus.altasherat.features.onboarding.presentation.ui.screens.OnBoardingThird
import com.solutionplus.altasherat.presentation.adapters.onboarding.ViewPagerAdapter
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    private val onBoardingVM: OnBoardingViewModel by viewModels()

    override fun viewInit() {
        handleViews()
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun subscribeToObservables() {
        handleEvents()
    }

    private fun handleViews() {
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
                onBoardingVM.processIntent(OnBoardingContract.OnBoardingAction.ContinueToHome)
                onBoardingVM.processIntent(OnBoardingContract.OnBoardingAction.SetOnBoardingShown)
            }
        }

        binding.dotsIndicator.attachTo(binding.viewPager)
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(onBoardingVM.singleEvent) {
            when (it) {
                OnBoardingContract.OnBoardingEvent.NavigateToHome -> navigateToHome()
            }
        }
    }

    private fun updateRightIconVisibility(lastIndex: Int) {
        binding.icRightRounded.visibility =
            if (binding.viewPager.currentItem == lastIndex) View.INVISIBLE else View.VISIBLE

        binding.backButton.visibility =
            if (binding.viewPager.currentItem == lastIndex) View.INVISIBLE else View.VISIBLE
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
    }

    override fun subscribeToObservables() {
    }


}