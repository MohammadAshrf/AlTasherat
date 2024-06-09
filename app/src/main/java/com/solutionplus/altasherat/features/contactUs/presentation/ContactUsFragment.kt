package com.solutionplus.altasherat.features.contactUs.presentation


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentContactUsBinding
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {


    private val viewModel: ContactUsViewModel by viewModels()
    private lateinit var countriesList: List<Country>

    override fun viewInit() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {

        viewModel.processIntent(ContactUsContract.ContactUsAction.GetCountries)
        subscribeToObservables()
    }

    override fun subscribeToObservables() {
        handleEvents()
    }


    private fun handleEvents() {
        collectFlowWithLifecycle(viewModel.singleEvent) {
            when (it) {
                is ContactUsContract.ContactUsEvent.GetCountries -> {
                    viewModel.processIntent(ContactUsContract.ContactUsAction.GetUpdatedUserFromLocal)
                    countriesList = it.country
                    setupCountrySpinner(countriesList)

                }

                is ContactUsContract.ContactUsEvent.GetUpdatedUserFromLocal -> handleUser(it.user)
            }
        }

    }

    private fun handleUser(user: User) {
        binding.firstNameEditText.setText(user.firstname)
        binding.emailEditText.setText(user.email)
        binding.etPhoneClient.setText(user.phone.number)
        binding.etCountryCode.setSelection(countriesList.indexOf(countriesList.find { country -> country.phoneCode == user.phone.countryCode }))

    }

    private fun setupCountrySpinner(countries: List<Country>) {
        val adapter = CountryCodeSpinnerAdapter(requireContext(), countries)
        binding.etCountryCode.adapter = adapter
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
}