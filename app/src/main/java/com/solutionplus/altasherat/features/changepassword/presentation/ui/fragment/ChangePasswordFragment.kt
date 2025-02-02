package com.solutionplus.altasherat.features.changepassword.presentation.ui.fragment

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint
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
                if (it is LeonException.Client.ResponseValidation) {
                    handleValidationErrors(it.errors) { errorMessage ->
                        errorMessage as String
                    }
                }
                if (it is LeonException.Local.RequestValidation) {
                    handleValidationErrors(it.errors) { errorKey ->
                        getString(errorKey as Int)
                    }
                }

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

    private fun handleValidationErrors(errors: Map<String, Any>, getErrorMessage: (Any) -> String) {
        errors[Validation.OLD_PASSWORD]?.let {
            setErrorWithTextWatcher(
                binding.etOldPassword,
                binding.textInputLayout2,
                getErrorMessage(it)
            )
        }
        errors[Validation.NEW_PASSWORD]?.let {
            setErrorWithTextWatcher(
                binding.etNewPassword,
                binding.textInputLayout3,
                getErrorMessage(it)
            )
        }
        errors[Validation.NEW_PASSWORD_CONFIRMATION]?.let {
            setErrorWithTextWatcher(
                binding.etReTypeNewPassword,
                binding.textInputLayout4,
                getErrorMessage(it)
            )
        }
    }

    private fun setErrorWithTextWatcher(
        editText: EditText,
        textInputLayout: TextInputLayout,
        errorMessage: String
    ) {
        editText.error = errorMessage
        textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                editText.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

}