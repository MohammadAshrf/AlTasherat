package com.solutionplus.altasherat.features.signup.presentation.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSignupBinding
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginState
import com.solutionplus.altasherat.features.signup.presentation.ui.adapter.CountryAdapter
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.OnSignupActionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment :BaseFragment<FragmentSignupBinding>(), OnSignupActionListener {

    private val viewModel: SignupViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) { }

    override fun viewInit() { }

    override fun subscribeToObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun renderState(state: SignUpState){
        when (state) {
            is SignUpState.Error -> { }
            is SignUpState.Loading -> { }
            is SignUpState.Success -> { Toast.makeText(requireContext(), "You signed up successfully", Toast.LENGTH_SHORT).show()}
        }
    }

    override fun onSignupAction() {
        if (validateLoginDetails()) {
            val firstName = binding.etFirstname.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val phoneNumber = binding.etPhoneClient.text.toString()
            val countryCode = binding.etCountruCode
            val password = binding.etPassword.text.toString()
            viewModel.handleIntent(
                SignUpIntent.SignUp(
                    firstName, lastName, email, phoneNumber, "0020", password
                )
            )
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            binding.etFirstname.text?.trim()?.length !in 3..15 -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_valid_first_name), true)
                false
            }
            binding.etLastName.text?.trim()?.length !in 3..15 -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_valid_last_name), true)
                false
            }
            binding.etEmail.text?.trim()?.length !in 1..15 || !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches() -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_valid_email), true)
                false
            }
            binding.etPhoneClient.text?.trim()?.length !in 9..15 || !TextUtils.isDigitsOnly(binding.etPhoneClient.text.toString()) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_valid_phone), true)
                false
            }
            binding.etPassword.text?.trim()?.length !in 8..50 -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_valid_password), true)
                false
            }
            else -> {
                true
            }
        }
    }
}