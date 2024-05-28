package com.solutionplus.altasherat.features.deleteaccount.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentDeleteAccountBinding
import com.solutionplus.altasherat.databinding.FragmentGotoDeleteAccountBinding

class GotoDeleteAccountFragment : BaseFragment<FragmentGotoDeleteAccountBinding>() {
    override fun onFragmentReady(savedInstanceState: Bundle?) { }

    override fun subscribeToObservables() { }

    override fun viewInit() {
        binding.txtDeleteAccount.setOnClickListener {
            findNavController().navigate(R.id.action_gotoDeleteAccountFragment_to_deleteAccountFragment)
        }
        binding.imgDeleteAccount.setOnClickListener {
            findNavController().navigate(R.id.action_gotoDeleteAccountFragment_to_deleteAccountFragment)
        }
    }

}