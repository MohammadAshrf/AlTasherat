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
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.domain.models.Country
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
        viewStateObserver()
        eventObserver()
    }

    private fun viewStateObserver() {
        collectFlowWithLifecycle(viewModel.viewState) {
            renderState(it)
        }

    }

    private fun eventObserver() {
        collectFlowWithLifecycle(viewModel.singleEvent) {
            handleEvent(it)
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
                if (it is LeonException.Local.RequestValidation) {
                    val errorMessages = it.errors
                    errorMessages[FIRST_NAME]?.let { binding.etFirstname.error = getString(it) }
                    errorMessages[LAST_NAME]?.let { binding.etLastName.error = getString(it) }
                    errorMessages[PASSWORD]?.let {
                        binding.etPassword.error = getString(it)
                        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                    errorMessages[PHONE]?.let { binding.etPhoneClient.error = getString(it) }
                    errorMessages[EMAIL]?.let { binding.etEmail.error = getString(it) }
                }
                if (it is LeonException.Client.ResponseValidation) {
                    val errors = it.errors
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

    private fun handleEvent(event: SignUpContract.SignupEvent) {
        when (event) {
            is SignUpContract.SignupEvent.SignupSuccess -> {
                gotoHomeActivity()
                showSnackBar()
            }

            is SignUpContract.SignupEvent.GetSelectedCountry -> {
                binding.etCountryCode.setSelection(event.country.id - 1)
            }

            is SignUpContract.SignupEvent.GetCountries -> {
                setupCountrySpinner(event.country)
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
        binding.etCountryCode.setSelection(
            countries.indexOf(adapter.getItem(0))
        )
        binding.etCountryCode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                binding.etCountryCode.adapter.getItem(position) as Country
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    override fun onSignupAction() {
        val firstName = binding.etFirstname.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val phoneNumber = binding.etPhoneClient.text.toString()
        val countryCode = (binding.etCountryCode.selectedItem as Country).phoneCode
        val countryId = (binding.etCountryCode.selectedItem as Country).id
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