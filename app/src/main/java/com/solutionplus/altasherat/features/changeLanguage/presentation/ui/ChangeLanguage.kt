package com.solutionplus.altasherat.features.changeLanguage.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentChangeLanguageBinding
import com.solutionplus.altasherat.features.language.presentation.ui.LanguageContract
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import java.util.Locale

class ChangeLanguage : BaseFragment<FragmentChangeLanguageBinding>() {
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
            if (binding.arabicRadioBtn.isChecked) {
                updateLocale("ar")
            } else {
                updateLocale("en")
            }
            activity?.recreate()

            findNavController().popBackStack()
        }
    }


    private fun updateLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        return requireContext().createConfigurationContext(config)
    }
}