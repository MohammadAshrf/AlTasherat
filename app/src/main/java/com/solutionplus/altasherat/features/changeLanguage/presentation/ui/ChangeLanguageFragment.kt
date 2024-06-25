package com.solutionplus.altasherat.features.changeLanguage.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentChangeLanguageBinding
import com.solutionplus.altasherat.features.language.presentation.Language
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageContract
import com.solutionplus.altasherat.presentation.adapters.singleSelection.SingleSelection
import com.solutionplus.altasherat.presentation.adapters.singleSelection.SingleSelectionAdapter
import com.solutionplus.altasherat.presentation.adapters.singleSelection.SingleSelectionCallback
import com.solutionplus.altasherat.presentation.adapters.singleSelection.SingleSelectionViewType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeLanguageFragment : BaseFragment<FragmentChangeLanguageBinding>(),
    SingleSelectionCallback {

    private val languageVM: ChangeLanguageViewModel by viewModels()

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
                    ChangeLanguageContract.ChangeLanguageAction.StartLanguageWorker(selectedLanguage.currentLocale)
                )
            findNavController().popBackStack()
        }


    override fun viewInit() = with(binding) {
        AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag()
            ?.let { language ->
                items.first { (it as Language).currentLocale == language }.selected = true
            }
        recyclerSingleSelection.adapter = adapter
        adapter.setItems(items)
        adapter.setSelectedItem(items.first { it.selected })

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(languageVM.singleEvent) {
            when (it) {
                is ChangeLanguageContract.ChangeLanguageEvent.LanguageWorkerStarted -> changeAppLocale(it.language)
            }
        }
    }

    private fun changeAppLocale(language: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {}

    override fun subscribeToObservables() {
        handleEvents()
    }
}