package com.solutionplus.altasherat.features.signup.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSignupBinding
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.OnSignupActionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(), OnSignupActionListener {

    private val viewModel: SignupViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        subscribeToObservables()
    }

    override fun viewInit() {}

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

    private fun renderState(state: SignUpContract.SignUpState) {
        CoroutineScope(Dispatchers.Main).launch {
            if (state.isLoading) {
                showLoading(resources.getString(R.string.please_wait))
            } else {
                hideLoading()
            }
            state.exception?.let {
                handleHttpExceptions(it)
            }
        }
    }

    private fun handleEvent(event: SignUpContract.SignupEvent) {
        when (event) {
            is SignUpContract.SignupEvent.SignupSuccess -> {
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
                showSnackBar(resources.getString(R.string.login_success), false)
                requireActivity().finish()
            }
        }
    }

    private fun setupCountrySpinner(countries: List<Country>) {
        val adapter = CountryCodeSpinnerAdapter(requireContext(), countries)
        binding.etCountruCode.adapter = adapter
        if (countries.isNotEmpty()) {
            binding.etCountruCode.setSelection(0)
        }
    }

    override fun onSignupAction() {
            val firstName = binding.etFirstname.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val phoneNumber = binding.etPhoneClient.text.toString()
            val countryCode = (binding.etCountruCode.selectedItem as Country).phoneCode
            val countryId = (binding.etCountruCode.selectedItem as Country).id
            val password = binding.etPassword.text.toString()
            viewModel.onActionTrigger(
                SignUpContract.SignupActions.Signup(
                    firstName, lastName, email, phoneNumber, countryCode, countryId, password
                )
            )

    }

    companion object {
        val logger = getClassLogger()
    }
}