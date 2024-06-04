package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.constants.Validation.PASSWORD
import com.solutionplus.altasherat.common.data.constants.Validation.PHONE
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.OnLoginActionListener
import dagger.hilt.android.AndroidEntryPoint
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

    override fun viewInit() {
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_resetPasswordByPhoneFragment)
        }
    }

    private fun handleEvent(event: LoginContract.LoginEvents) {
        when (event) {
            is LoginContract.LoginEvents.LoginSuccess -> {
                gotoHomeActivity()
                showSnackBar()
            }

            is LoginContract.LoginEvents.GetSelectedCountry -> {
                setDefaultCountry(event.country)
            }

            is LoginContract.LoginEvents.GetCountries -> {
                setupCountrySpinner(event.country)
            }

            is LoginContract.LoginEvents.LoginFailure -> {
                if (event.exception is LeonException.Local.RequestValidation) {
                    val errorMessages = event.exception.errors
                    errorMessages[PASSWORD]?.let {
                        binding.etPassword.error = getString(it)
                        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                    errorMessages[PHONE]?.let { binding.etPhoneClient.error = getString(it) }
                }

                if (event.exception is LeonException.Client.ResponseValidation) {
                    val errorMessages = event.exception.errors
                    errorMessages[PASSWORD]?.let {
                        binding.etPassword.error = it
                        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                    errorMessages[PHONE]?.let {
                        binding.etPhoneClient.error = it
                        getClassLogger().debug("Phone error: $it") // Add this line
                    }
                }

            }
        }
    }

    private fun gotoHomeActivity() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
    }

    private fun showSnackBar() {
        showSnackBar(resources.getString(R.string.login_success), false)
        requireActivity().finish()
    }


    private fun setupCountrySpinner(countries: List<Country>) {
        val adapter = CountryCodeSpinnerAdapter(requireContext(), countries)
        binding.etCountruCode.adapter = adapter
        binding.etCountruCode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                binding.etCountruCode.adapter.getItem(position) as Country
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setDefaultCountry(country: Country?) {
        country?.let {
            val adapter = binding.etCountruCode.adapter as CountryCodeSpinnerAdapter
            val position = adapter.getPosition(country)
            if (position >= 0) {
                binding.etCountruCode.setSelection(position)
            }
        }
    }

    override fun onLoginAction() {
        binding.etPassword.error = null
        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE


        val phoneNumber = binding.etPhoneClient.text.toString()
        val password = binding.etPassword.text.toString()
        val countryCode = (binding.etCountruCode.selectedItem as Country).phoneCode
        viewModel.onActionTrigger(
            LoginContract.LoginActions.LoginWithPhone(phoneNumber, countryCode, password)
        )
    }

}