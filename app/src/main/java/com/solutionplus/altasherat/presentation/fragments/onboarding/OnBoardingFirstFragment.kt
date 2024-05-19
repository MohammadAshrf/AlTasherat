package com.solutionplus.altasherat.presentation.fragments.onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.repository.local.DataStoreKeyValueStorage
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOnBoardingBinding
import kotlinx.coroutines.launch

class OnBoardingFirstFragment : BaseFragment<FragmentOnBoardingBinding>() {
    override fun viewInit() {
        binding.textOnBoarding.text = "Welcome to OnBoardingFirstFragment"
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.buttonOnBoarding.setOnClickListener {
            lifecycleScope.launch {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_homeActivity)
    }

    override fun subscribeToObservables() {
    }


}