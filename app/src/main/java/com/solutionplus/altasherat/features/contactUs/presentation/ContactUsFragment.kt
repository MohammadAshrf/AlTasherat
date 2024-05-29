package com.solutionplus.altasherat.features.contactUs.presentation


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentContactUsBinding
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {
    private val adapter: CountryCodeSpinnerAdapter by lazy {
        CountryCodeSpinnerAdapter(requireContext(), emptyList())
    }

    private val contactUsVM: ContactUsViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE
        contactUsVM.processIntent(ContactUsContract.ContactUsAction.GetCountries)
    }

    override fun subscribeToObservables() {
        handleEvents()
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(contactUsVM.singleEvent) {
            when (it) {
                is ContactUsContract.ContactUsEvent.CountriesIndex ->{
                    val spinnerAdapter = CountryCodeSpinnerAdapter(requireContext(), it.countries)
                    binding.phoneNumberPicker.etCountruCode.adapter = spinnerAdapter
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.VISIBLE
    }
    override fun viewInit() {
        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }
    private fun setupCountrySpinner(countries: List<Country>) {
        val adapter = CountryCodeSpinnerAdapter(requireContext(), countries)
        binding.phoneNumberPicker.etCountruCode.adapter = adapter
    }
}