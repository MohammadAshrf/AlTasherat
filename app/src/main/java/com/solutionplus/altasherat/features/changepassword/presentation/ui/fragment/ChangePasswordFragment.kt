package com.solutionplus.altasherat.features.changepassword.presentation.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun subscribeToObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.viewState.collect { state ->
                        getClassLogger().info(state.exception.toString())
                        state.exception?.let {
                            handleHttpExceptions(it)
                        }
                        if (state.isLoading) {
                            showLoading()
                        } else {
                            hideLoading()
                        }
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
        binding.imgback.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handleEvent(event: ChangePasswordContract.ChangePasswordEvents) {
        when (event) {
            is ChangePasswordContract.ChangePasswordEvents.ChangePasswordSuccess -> {
                showSnackBar(resources.getString(R.string.login_success), false)
                requireActivity().finish()
                Toast.makeText(
                    requireContext(),
                    "Your Password Changed successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.VISIBLE
    }
}