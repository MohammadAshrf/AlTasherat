package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation.PASSWORD
import com.solutionplus.altasherat.common.data.constants.Validation.PHONE
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginContract.LoginAction.GetCountries
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.OnLoginActionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), OnLoginActionListener {

    private val viewModel: LoginViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.processIntent(GetCountries)
        viewModel.processIntent(LoginContract.LoginAction.GetSelectedCountry)
        subscribeToObservables()
    }

    override fun subscribeToObservables() {
        renderState()
        handleEvent()
    }

    private fun renderState() {
        collectFlowWithLifecycle(viewModel.viewState) { it ->
            it.exception?.let {
                handleHttpExceptions(it)
                if (it is LeonException.Local.RequestValidation) {
                    handleValidationErrors(it.errors) { errorKey ->
                        getString(errorKey as Int)
                    }
                }

                if (it is LeonException.Client.ResponseValidation) {
                    handleValidationErrors(it.errors) { errorMessage ->
                        errorMessage as String
                    }
                }
            }
            if (it.isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    override fun viewInit() {
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_resetPasswordByPhoneFragment)
        }
    }

    private fun handleEvent() {
        collectFlowWithLifecycle(viewModel.singleEvent) {
            when (it) {
                is LoginContract.LoginEvents.LoginSuccess -> {
                    gotoHomeActivity()
                    showSnackBar()
                }

                is LoginContract.LoginEvents.GetSelectedCountry -> {
                    binding.etCountryCode.setSelection(it.country.id - 1)
                }

                is LoginContract.LoginEvents.GetCountries -> {
                    setupCountrySpinner(it.country)
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
        binding.etCountryCode.adapter = adapter
        // Find the default country (assuming it's the first one) and set its position
        val defaultCountry = countries.firstOrNull() // Use firstOrNull for safety
        val defaultPosition = countries.indexOf(defaultCountry)
        if (defaultPosition != -1) {
            binding.etCountryCode.setSelection(defaultPosition)
        }

        binding.etCountryCode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.etCountryCode.adapter.getItem(position) as Country
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onLoginAction() {
        binding.etPassword.error = null
        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE


        val phoneNumber = binding.etPhoneClient.text.toString()
        val password = binding.etPassword.text.toString()
        val countryCode = (binding.etCountryCode.selectedItem as Country).phoneCode
        viewModel.processIntent(
            LoginContract.LoginAction.LoginWithPhone(phoneNumber, countryCode, password)
        )
    }

    private fun handleValidationErrors(errors: Map<String, Any>, getErrorMessage: (Any) -> String) {
        errors[PASSWORD]?.let {
            binding.etPassword.error = getErrorMessage(it)
            binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE
            binding.etPassword.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    binding.etPassword.error = null
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
        errors[PHONE]?.let { binding.etPhoneClient.error = getErrorMessage(it) }
    }

}