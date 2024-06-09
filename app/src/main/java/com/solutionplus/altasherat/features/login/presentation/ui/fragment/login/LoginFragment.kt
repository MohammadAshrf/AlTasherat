package com.solutionplus.altasherat.features.login.presentation.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Validation.PASSWORD
import com.solutionplus.altasherat.common.data.constants.Validation.PHONE
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.OnLoginActionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), OnLoginActionListener {

    private val viewModel: LoginViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        subscribeToObservables()
        viewModel.onActionTrigger(LoginContract.LoginActions.GetCountries)

    }

    override fun subscribeToObservables() {
        viewStateObserver()
        eventObserver()
    }

    private fun viewStateObserver() {
        collectFlowWithLifecycle(viewModel.viewState) {
            getClassLogger().info(it.exception.toString())
            it.exception?.let {
                handleHttpExceptions(it)

                if (it is LeonException.Local.RequestValidation) {
                    val errorMessages = it.errors
                    errorMessages[PASSWORD]?.let {
                        binding.etPassword.error = getString(it)
                        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                    errorMessages[PHONE]?.let { binding.etPhoneClient.error = getString(it) }
                }

                if (it is LeonException.Client.ResponseValidation) {
                    val errorMessages = it.errors
                    errorMessages[PASSWORD]?.let {
                        binding.etPassword.error = it
                        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_NONE
                    }
                    errorMessages[PHONE]?.let {
                        binding.etPhoneClient.error = it
                        getClassLogger().debug("Phone error: $it")
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

    private fun eventObserver() {
        collectFlowWithLifecycle(viewModel.singleEvent) {
            handleEvent(it)
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
                binding.etCountryCode.setSelection(event.country.id - 1)
            }

            is LoginContract.LoginEvents.GetCountries -> {
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

    override fun onLoginAction() {
        binding.etPassword.error = null
        binding.textInputLayout2.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE


        val phoneNumber = binding.etPhoneClient.text.toString()
        val password = binding.etPassword.text.toString()
        val countryCode = (binding.etCountryCode.selectedItem as Country).phoneCode
        viewModel.onActionTrigger(
            LoginContract.LoginActions.LoginWithPhone(phoneNumber, countryCode, password)
        )
    }

}