package com.solutionplus.altasherat.features.about.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>() {
    override fun viewInit() {
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {}

    override fun subscribeToObservables() {}


}