package com.solutionplus.altasherat.features.changepassword.presentation.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentChangePasswordBinding
import com.solutionplus.altasherat.features.changepassword.domain.repository.local.IChangePasswordLocalDS
import com.solutionplus.altasherat.features.changepassword.presentation.ui.fragment.ChangePasswordViewModel
import com.solutionplus.altasherat.features.signup.presentation.ui.SignUpContract
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {}

    override fun subscribeToObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.viewState.collect { state ->
                        renderState(state)
                    }

                }
                launch {
                    viewModel.singleEvent.collect { event ->
                        handleEvent(event)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun viewInit() {
        binding.btnSave.setOnClickListener {
            if (validateChangePasswordDetails()) {
                val oldPassword = binding.etOldPassword.text.toString()
                val newPassword = binding.etNewPassword.text.toString()
                val newPasswordConfirmation = binding.etReTypeNewPassword.text.toString()

                viewModel.onActionTrigger(
                    ChangePasswordContract.ChangePasswordActions.ChangePassword(
                        oldPassword,
                        newPassword,
                        newPasswordConfirmation
                    )
                )
            }
        }
        binding.imgback.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordFragment_to_homeActivity)
        }
    }

    private fun renderState(state: ChangePasswordContract.ChangePasswordState) {
        CoroutineScope(Dispatchers.Main).launch {
            if (state.isLoading) {
                showLoading(resources.getString(R.string.please_wait))
            } else {
                hideLoading()
            }
            state.exception?.let {
                Toast.makeText(requireContext(), it.message ?: "Unknown error", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun handleEvent(event: ChangePasswordContract.ChangePasswordEvents) {
        when (event) {
            is ChangePasswordContract.ChangePasswordEvents.ChangePasswordSuccess -> {
                Toast.makeText(
                    requireContext(),
                    "Your Password Changed successfully",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_changePasswordFragment_to_homeActivity)
            }

            is ChangePasswordContract.ChangePasswordEvents.ChangePasswordError -> {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateChangePasswordDetails(): Boolean {
        return when {
            binding.etOldPassword.text.isNullOrEmpty() -> {
                showErrorSnackBar("Please enter the old password", true)
                false
            }

            binding.etNewPassword.text.isNullOrEmpty() || binding.etNewPassword.text!!.length < 8 -> {
                showErrorSnackBar("Please enter a valid new password (at least 8 characters)", true)
                false
            }

            binding.etReTypeNewPassword.text.toString() != binding.etNewPassword.text.toString() -> {
                showErrorSnackBar("New password and confirmation do not match", true)
                false
            }

            else -> true
        }
    }
}