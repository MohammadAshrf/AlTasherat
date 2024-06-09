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
import com.google.android.material.textfield.TextInputLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
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

    }

    override fun subscribeToObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewStateObserver() }
                launch { eventObserver() }
            }
        }
    }

    private suspend fun viewStateObserver() {
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

    private suspend fun eventObserver() {
        viewModel.singleEvent.collect { event ->
            handleEvent(event)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun viewInit() {
        binding.btnSave.setOnClickListener {
            handleChangePassword()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handleChangePassword() {
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

    private fun handleEvent(event: ChangePasswordContract.ChangePasswordEvents) {
        when (event) {
            is ChangePasswordContract.ChangePasswordEvents.ChangePasswordSuccess -> {
                showSnackBar()
                showSuccessfulToast()
            }

            is ChangePasswordContract.ChangePasswordEvents.ChangePasswordError -> {


                if (event.exception is LeonException.Local.RequestValidation) {
                    val errorMessages = event.exception.errors
                    errorMessages[Validation.OLD_PASSWORD]?.let {
                        binding.etOldPassword.error = getString(it)
                        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE

                    }
                    errorMessages[Validation.NEW_PASSWORD]?.let {
                        binding.etNewPassword.error = getString(it)
                        binding.textInputLayout3.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                    errorMessages[Validation.NEW_PASSWORD_CONFIRMATION]?.let {
                        binding.etReTypeNewPassword.error = getString(it)
                        binding.textInputLayout4.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                }

                if (event.exception is LeonException.Client.ResponseValidation) {
                    val errorMessages = event.exception.errors
                    errorMessages[Validation.OLD_PASSWORD]?.let { binding.etOldPassword.error = it
                        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                    errorMessages[Validation.NEW_PASSWORD]?.let {
                        binding.etNewPassword.error = it
                        binding.etReTypeNewPassword.error = it
                        binding.textInputLayout3.endIconMode = TextInputLayout.END_ICON_NONE
                        binding.textInputLayout4.endIconMode = TextInputLayout.END_ICON_NONE

                    }
                }
            }
        }
    }

    private fun showSnackBar() {
        showSnackBar(resources.getString(R.string.login_success), false)
        requireActivity().finish()
    }

    private fun showSuccessfulToast() {
        Toast.makeText(requireContext(), "Your Password Changed Successfully", Toast.LENGTH_SHORT)
            .show()
    }


}