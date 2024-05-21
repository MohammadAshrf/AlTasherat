package com.solutionplus.altasherat.features.signup.presentation.ui

import android.content.Intent
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
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginContract
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.signup.presentation.ui.adapter.CountryAdapter
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.OnSignupActionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment :BaseFragment<FragmentSignupBinding>(), OnSignupActionListener {

    private val viewModel: SignupViewModel by viewModels()
    private val adapter: CountryAdapter by lazy {
        CountryAdapter(requireContext(), emptyList())
    }
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        subscribeToObservables()
    }

    override fun viewInit() { }

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
                launch {
                    viewModel.countries.collect { countries ->
                        setupCountrySpinner(countries)
                    }
                }
            }
        }
    }

    private fun renderState(state: SignUpContract.SignUpState){
        CoroutineScope(Dispatchers.Main).launch {
            if (state.isLoading)
            {
                showLoading(resources.getString(R.string.please_wait))
            }else{
                hideLoading()
            }
            state.exception?.let {
                Toast.makeText(requireContext(), it.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun handleEvent(event: SignUpContract.SignupEvent) {
        when (event) {
            is SignUpContract.SignupEvent.SignupSuccess -> {
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
                Toast.makeText(requireContext(), "You signed up successfully", Toast.LENGTH_SHORT).show()
            }
            is SignUpContract.SignupEvent.SignupError -> {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun setupCountrySpinner(countries: List<Country>) {
        adapter.updateCountries(countries)
        binding.etCountruCode.adapter = adapter
    }
    override fun onSignupAction() {
        if (validateLoginDetails()) {
            val firstName = binding.etFirstname.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val phoneNumber = binding.etPhoneClient.text.toString()
            val countryCode =(binding.etCountruCode.selectedItem as Country).code
            val password = binding.etPassword.text.toString()
            viewModel.onActionTrigger(
                SignUpContract.SignupActions.Signup(
                    firstName, lastName, email, phoneNumber, countryCode, password
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
            binding.etEmail.text?.trim()?.length !in 1..25 || !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches() -> {
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