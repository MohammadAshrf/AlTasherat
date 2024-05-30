package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.OnLoginActionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), OnLoginActionListener {

    private val viewModel: LoginViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        subscribeToObservables()
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
                launch {
                    viewModel.countries.collect { countries ->
                        setupCountrySpinner(countries)
                    }
                }
            }
        }
    }

    private fun setupCountrySpinner(countries: List<Country>) {
        val adapter = CountryCodeSpinnerAdapter(requireContext(), countries)
        binding.etCountruCode.adapter = adapter
    }

    override fun viewInit() {
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordByPhoneFragment)

        }
    }

    private fun handleEvent(event: LoginContract.LoginEvents) {
        when (event) {
            is LoginContract.LoginEvents.LoginSuccess -> {
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
                showSnackBar(resources.getString(R.string.login_success), false)
                requireActivity().finish()
            }

            is LoginContract.LoginEvents.LoginError -> {
              //  showSnackBar(event.message, true)

            }
        }
    }

    override fun onLoginAction() {
        val phoneNumber = binding.etPhoneClient.text.toString()
        val password = binding.etPassword.text.toString()
        val countryCode = (binding.etCountruCode.selectedItem as Country).phoneCode
        viewModel.onActionTrigger(
            LoginContract.LoginActions.LoginWithPhone(phoneNumber, countryCode, password)
        )
    }


}