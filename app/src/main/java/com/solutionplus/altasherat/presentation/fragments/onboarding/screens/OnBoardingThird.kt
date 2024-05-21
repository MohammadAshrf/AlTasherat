package com.solutionplus.altasherat.presentation.fragments.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IStorageKeyEnum
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingThirdBinding
import com.solutionplus.altasherat.feature.services.country.data.repository.CountriesRepository
import kotlinx.coroutines.launch

class OnBoardingThird() : BaseFragment<FragmentOnBoardingThirdBinding>() {

    override fun viewInit() {
        binding
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {

        binding.nextButton.setOnClickListener {
            lifecycleScope.launch {
                findNavController().navigate(R.id.action_onBoardingFragment_to_homeActivity)
//                onBoardingCompleted()
//                activity?.finish()
            }
        }

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        binding.icRightRounded.setOnClickListener {
            viewPager?.setCurrentItem(viewPager.currentItem - 1, true)
        }
    }

    override fun subscribeToObservables() {
    }

//    private suspend fun onBoardingCompleted() {
//    }


}