package com.solutionplus.altasherat.features.contactUs.presentation


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentContactUsBinding
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.services.country.adapters.CountriesSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactUsBinding>() {


    private val viewModel: ContactUsViewModel by viewModels()
    private lateinit var countriesList: List<Country>

    override fun viewInit() {
        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE
        viewModel.processIntent(ContactUsContract.ContactUsAction.GetCountries)
        subscribeToObservables()
   }

    override fun subscribeToObservables() {
        handleEvents()
    }


    private fun handleEvents() {
    collectFlowWithLifecycle(viewModel.singleEvent){
        when(it){
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
        binding.phoneNumberPicker.editTextPhone.setText(user.phone.number)
        binding.phoneNumberPicker.etCountruCode.setSelection(countriesList.indexOf(countriesList.find { country -> country.phoneCode == user.phone.countryCode }))

    }

    private fun setupCountrySpinner(countries: List<Country>) {
        val adapter = CountryCodeSpinnerAdapter(requireContext(), countries)
        binding.phoneNumberPicker.etCountruCode.adapter = adapter
        binding.phoneNumberPicker.etCountruCode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                binding.phoneNumberPicker.etCountruCode.adapter.getItem(position) as Country
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.VISIBLE
    }

}