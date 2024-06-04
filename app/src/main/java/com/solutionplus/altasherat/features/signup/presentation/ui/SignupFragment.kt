package com.solutionplus.altasherat.features.signup.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Validation.EMAIL
import com.solutionplus.altasherat.common.data.constants.Validation.FIRST_NAME
import com.solutionplus.altasherat.common.data.constants.Validation.LAST_NAME
import com.solutionplus.altasherat.common.data.constants.Validation.PASSWORD
import com.solutionplus.altasherat.common.data.constants.Validation.PHONE
import com.solutionplus.altasherat.common.data.model.exception.LeonException
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
                launch { viewStateObserver() }
                launch { eventObserver() }
            }
        }
    }

    private suspend fun viewStateObserver() {
        viewModel.viewState.collect { state ->
            renderState(state)
        }

    }

    private suspend fun eventObserver() {
        viewModel.singleEvent.collect { event ->
            handleEvent(event)
        }
    }

    private fun renderState(state: SignUpContract.SignUpState) {
        CoroutineScope(Dispatchers.Main).launch {
            if (state.isLoading)
                showLoading(resources.getString(R.string.please_wait))
            else
                hideLoading()

            state.exception?.let {
                handleHttpExceptions(it)
            }
        }
    }

    private fun handleEvent(event: SignUpContract.SignupEvent) {
        when (event) {
            is SignUpContract.SignupEvent.SignupSuccess -> {
                gotoHomeActivity()
                showSnackBar()
            }

            is SignUpContract.SignupEvent.GetSelectedCountry -> {
                setDefaultCountry(event.country)
            }

            is SignUpContract.SignupEvent.GetCountries -> {
                setupCountrySpinner(event.country)
            }

            is SignUpContract.SignupEvent.SignupFailure -> {
                if (event.exception is LeonException.Local.RequestValidation) {
                    val errorMessages =event.exception.errors
                    errorMessages[FIRST_NAME]?.let { binding.etFirstname.error = getString(it) }
                    errorMessages[LAST_NAME]?.let { binding.etLastName.error = getString(it) }
                    errorMessages[PASSWORD]?.let {
                        binding.etPassword.error = getString(it)
                        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                    errorMessages[PHONE]?.let { binding.etPhoneClient.error = getString(it) }
                    errorMessages[EMAIL]?.let { binding.etEmail.error = getString(it) }
                }
                if (event.exception is LeonException.Client.ResponseValidation) {
                    val errors = event.exception.errors
                    errors[PASSWORD]?.let {
                        binding.etPassword.error = it
                        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                    errors[PHONE]?.let { binding.etPhoneClient.error = it }
                    errors[EMAIL]?.let { binding.etEmail.error = it }
                    errors[FIRST_NAME]?.let { binding.etFirstname.error = it }
                    errors[LAST_NAME]?.let { binding.etLastName.error = it }
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

    override fun onSignupAction() {
        val firstName = binding.etFirstname.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val phoneNumber = binding.etPhoneClient.text.toString()
        val countryCode = (binding.etCountruCode.selectedItem as Country).phoneCode
        val countryId = (binding.etCountruCode.selectedItem as Country).id
        val password = binding.etPassword.text.toString()

        binding.etPassword.error = null
        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE

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