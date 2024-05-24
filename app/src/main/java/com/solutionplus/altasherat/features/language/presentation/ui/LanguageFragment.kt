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
        binding.continueButton.setOnClickListener {
            languageVM.onActionTrigger(LanguageContract.LanguageAction.ContinueToOnBoarding)
        }
        binding.englishRadioBtn.setOnCheckedChangeListener {
            btn, isChecked ->
            btn.setBackgroundResource(if (isChecked) R.drawable.item_default_single_radio_selection else R.drawable.item_single_radio_selection)
            languageVM.onActionTrigger(LanguageContract.LanguageAction.StartLanguageWorker)
        }
        binding.arabicRadioBtn.setOnCheckedChangeListener {
            btn, isChecked ->
            btn.setBackgroundResource(if (isChecked) R.drawable.item_default_single_radio_selection else R.drawable.item_single_radio_selection)
            languageVM.onActionTrigger(LanguageContract.LanguageAction.StartEnLanguageWorker)
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        languageVM.onActionTrigger(LanguageContract.LanguageAction.GetArCountries)
        languageVM.onActionTrigger(LanguageContract.LanguageAction.GetEnCountries)
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

                LanguageContract.LanguageEvent.NavigateToOnBoarding -> navigateToOnBoarding()
                LanguageContract.LanguageEvent.LanguageWorkerStarted -> changeAppLocale()
            }
        }
    }

    private fun changeAppLocale() {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
    }

    private fun navigateToOnBoarding() {
        findNavController().navigate(R.id.action_languageFragment_to_onBoardingFragment)
    }
}