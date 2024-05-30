package com.solutionplus.altasherat.features.changeLanguage.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentChangeLanguageBinding
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageContract
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageViewModel
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ChangeLanguage : BaseFragment<FragmentChangeLanguageBinding>() {

    private val languageVM: LanguageViewModel by viewModels()


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun subscribeToObservables() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.VISIBLE
    }


    override fun viewInit() {
        binding.btnSave.setOnClickListener {
            handleViews()
        }
    }

    private fun handleViews() {
        if (binding.arabicRadioBtn.isChecked) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
            languageVM.processIntent(LanguageContract.LanguageAction.StartLanguageWorker("ar"))

        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            languageVM.processIntent(LanguageContract.LanguageAction.StartLanguageWorker("en"))
        }
        findNavController().popBackStack()

    }

}