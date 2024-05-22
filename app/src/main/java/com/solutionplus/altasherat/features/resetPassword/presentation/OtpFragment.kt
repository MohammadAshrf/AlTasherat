package com.solutionplus.altasherat.features.resetPassword.presentation


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentOtpBinding


class OtpFragment : BaseFragment<FragmentOtpBinding>() {
    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {
        binding.btnSendOtp.setOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_enterNewPasswordFragment)

        }


    }

}