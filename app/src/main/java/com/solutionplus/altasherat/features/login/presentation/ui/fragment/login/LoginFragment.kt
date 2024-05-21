package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.presentation.ui.activity.main.ForgetPasswordActivity
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.OnLoginActionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), OnLoginActionListener {

    private val viewModel: LoginViewModel by viewModels()

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

    override fun viewInit() {
        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(requireActivity(), ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun renderState(state: LoginContract.LoginState) {
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

    private fun handleEvent(event: LoginContract.LoginEvents) {
        when (event) {
            is LoginContract.LoginEvents.LoginSuccess -> {
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
                Toast.makeText(requireContext(), "You logged in successfully", Toast.LENGTH_SHORT)
                    .show()
            }

            is LoginContract.LoginEvents.LoginError -> {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoginAction() {
        if (validateLoginDetails()) {
            val phoneNumber = binding.etPhoneClient.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.onActionTrigger(
                LoginContract.LoginActions.LoginWithPhone(phoneNumber, "0020", password)
            )
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
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