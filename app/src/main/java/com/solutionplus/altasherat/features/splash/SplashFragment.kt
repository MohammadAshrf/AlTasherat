package com.solutionplus.altasherat.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSplashBinding
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val splashVM: SplashViewModel by viewModels()
    override fun viewInit() {
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        splashVM.onActionTrigger(SplashContract.SplashAction.IsOnBoardingShown)
    }

    override fun subscribeToObservables() {
        handleEvents()
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(splashVM.singleEvent) {
            when (it) {
                is SplashContract.SplashEvent.NavigateToOnBoarding -> navigateToOnboarding()
                is SplashContract.SplashEvent.NavigateToHome -> startHomeActivity()
            }
        }
    }

    private fun navigateToOnboarding() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_onBoardingFirstFragment)
        }, 1500)
    }

    private fun startHomeActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(
                    requireContext(),
                    HomeActivity::class.java
                )
            ).also { requireActivity().finish() }
        }, 1500)
    }
}