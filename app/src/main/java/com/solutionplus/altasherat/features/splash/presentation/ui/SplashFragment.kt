package com.solutionplus.altasherat.features.splash.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
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
        splashVM.processIntent(SplashContract.SplashAction.HasCountries)
       //AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
    }

    override fun subscribeToObservables() {
        handleEvents()
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(splashVM.singleEvent) {
            when (it) {
                is SplashContract.SplashEvent.NavigateToLanguage -> navigateToLanguage()
                is SplashContract.SplashEvent.NavigateToHome -> startHomeActivity()
                is SplashContract.SplashEvent.NavigateToOnBoarding -> navigateToOnBoarding()
            }
        }
    }

    private fun navigateToOnBoarding() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
        }, 1200)
    }

    private fun navigateToLanguage() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_languageFragment)
        }, 1200)
    }

    private fun startHomeActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(
                    requireContext(),
                    HomeActivity::class.java
                )
            ).also { requireActivity().finish() }
        }, 1200)
    }
}