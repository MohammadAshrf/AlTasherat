package com.solutionplus.altasherat.features.language.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLanguageBinding
import com.solutionplus.altasherat.features.language.presentation.Language
import com.solutionplus.altasherat.features.services.country.adapters.CountriesSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.presentation.adapters.singleSelection.SingleSelection
import com.solutionplus.altasherat.presentation.adapters.singleSelection.SingleSelectionAdapter
import com.solutionplus.altasherat.presentation.adapters.singleSelection.SingleSelectionCallback
import com.solutionplus.altasherat.presentation.adapters.singleSelection.SingleSelectionViewType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>(), SingleSelectionCallback {
    private val languageVM: LanguageViewModel by viewModels()

    private val adapter: SingleSelectionAdapter by lazy {
        SingleSelectionAdapter(SingleSelectionViewType.SELECTION_RADIO, this)
    }

    private val items: List<SingleSelection> by lazy {
        listOf(
            Language(1, getString(R.string.arabic), "ar", selected = false, R.drawable.arabic),
            Language(2, getString(R.string.english), "en", selected = false, R.drawable.english)
        )
    }

    override fun onSingleItemSelected(selectedItem: SingleSelection) {
        val selectedLanguage = selectedItem as Language
        languageVM
            .processIntent(
                LanguageContract.LanguageAction.StartLanguageWorker(selectedLanguage.currentLocale)
            )
    }

    override fun viewInit() = with(binding) {
        AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag()
            ?.let { language ->
                items.first { (it as Language).currentLocale == language }.selected = true
            }
        recyclerSingleSelection.adapter = adapter
        adapter.setItems(items)
        adapter.setSelectedItem(items.first { it.selected })
        binding.continueButton.setOnClickListener {
            languageVM.processIntent(LanguageContract.LanguageAction.ContinueToOnBoarding)
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        languageVM.processIntent(LanguageContract.LanguageAction.GetCountriesFromLocal)
        languageVM.processIntent(LanguageContract.LanguageAction.GetSelectedCountry)
    }

    override fun subscribeToObservables() {
        handleEvents()
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(languageVM.singleEvent) {
            when (it) {
                is LanguageContract.LanguageEvent.CountriesIndex -> {
                    handleCountrySpinner(it)
                }

                is LanguageContract.LanguageEvent.NavigateToOnBoarding -> navigateToOnBoarding()
                is LanguageContract.LanguageEvent.LanguageWorkerStarted -> changeAppLocale(it.language)
                is LanguageContract.LanguageEvent.SaveSelectedCountry -> {}
                is LanguageContract.LanguageEvent.GetSelectedCountry -> {
                    binding.countriesSpinner.setSelection(it.country.id - 1)
                }
            }
        }
    }

    private fun handleCountrySpinner(it: LanguageContract.LanguageEvent.CountriesIndex) {
        val spinnerAdapter = CountriesSpinnerAdapter(requireContext(), it.countries)
        binding.countriesSpinner.adapter = spinnerAdapter
        binding.countriesSpinner.setSelection(
            it.countries.indexOf(spinnerAdapter.getItem(0))
        )
        binding.countriesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedCountry =
                        binding.countriesSpinner.adapter.getItem(position) as Country
                    languageVM.processIntent(
                        LanguageContract.LanguageAction.SaveSelectedCountry(
                            selectedCountry
                        )
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
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