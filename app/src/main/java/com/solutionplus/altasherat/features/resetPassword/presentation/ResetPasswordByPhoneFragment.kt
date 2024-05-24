package com.solutionplus.altasherat.features.resetPassword.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentResetPasswordByPhoneBinding

class ResetPasswordByPhoneFragment : BaseFragment<FragmentResetPasswordByPhoneBinding>() {

    override fun onFragmentReady(savedInstanceState: Bundle?) {

    }

    override fun subscribeToObservables() {
        // Implement this method if you have any observables to subscribe to
    }

    override fun viewInit() {
        binding.btnSendPhone.setOnClickListener {
            findNavController().navigate(R.id.action_resetPasswordByPhoneFragment_to_otpFragment)
        }
    }

    companion object {
        private val logger = getClassLogger()
    }
}