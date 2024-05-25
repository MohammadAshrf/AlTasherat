package com.solutionplus.altasherat.features.language.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguageBinding
import com.solutionplus.altasherat.features.language.presentation.adapters.CountriesSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {
    private val languageVM: LanguageViewModel by viewModels()
    override fun viewInit() {
        binding.arabicRadioBtn.setOnClickListener {
            binding.englishRadioBtn.isEnabled = true
            binding.arabicRadioBtn.isEnabled = false
            languageVM.processIntent(LanguageContract.LanguageAction.StartLanguageWorker("ar"))
        }
        binding.englishRadioBtn.setOnClickListener {
            binding.englishRadioBtn.isEnabled = false
            binding.arabicRadioBtn.isEnabled = true
            languageVM.processIntent(LanguageContract.LanguageAction.StartLanguageWorker("en"))
        }
        binding.continueButton.setOnClickListener {
            languageVM.processIntent(LanguageContract.LanguageAction.ContinueToOnBoarding)
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        languageVM.processIntent(LanguageContract.LanguageAction.GetCountries)
//        languageVM.processIntent(LanguageContract.LanguageAction.GetEnCountries)
    }

    override fun subscribeToObservables() {
        handleEvents()
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(languageVM.singleEvent) {
            when (it) {
                is LanguageContract.LanguageEvent.CountriesIndex -> {
                    val spinnerAdapter = CountriesSpinnerAdapter(requireContext(), it.countries)
                    binding.countriesSpinner.adapter = spinnerAdapter
                }

                is LanguageContract.LanguageEvent.NavigateToOnBoarding -> navigateToOnBoarding()

                is LanguageContract.LanguageEvent.LanguageWorkerStarted -> changeAppLocale(it.language)
            }
        }
    }

    private fun changeAppLocale(language: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
    }

    private fun navigateToOnBoarding() {
        findNavController().navigate(R.id.action_languageFragment_to_onBoardingFragment)
    }
}