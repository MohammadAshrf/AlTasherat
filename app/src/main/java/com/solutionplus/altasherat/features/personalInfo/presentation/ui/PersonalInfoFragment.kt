package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import com.solutionplus.altasherat.features.services.country.adapters.CountriesSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>() {

    private val personalInfoVM: PersonalInfoViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun viewInit() {
        handleViews()
    }

    private fun validatePersonalInfo(): Boolean {
        return when {
            binding.firstNameEditText.text?.trim()?.length !in 3..15 -> {
                showSnackBar(
                    resources.getString(R.string.err_msg_enter_valid_first_name), true
                )
                false
            }

            (binding.middleNameEditText.text?.trim()?.length ?: 0) > 15 -> {
                showSnackBar(
                    resources.getString(R.string.err_msg_enter_valid_middle_name), true
                )
                false
            }

            binding.lastNameEditText.text?.trim()?.length !in 3..15 -> {
                showSnackBar(
                    resources.getString(R.string.err_msg_enter_valid_last_name), true
                )
                false
            }

            binding.emailEditText.text?.trim()?.length !in 0..50 || !Patterns.EMAIL_ADDRESS.matcher(
                binding.emailEditText.text.toString()
            ).matches() -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_valid_email), true)
                false
            }

            binding.phoneEditText.text?.trim()?.length !in 9..15 || !TextUtils.isDigitsOnly(binding.phoneEditText.text.toString()) -> {
                showSnackBar(resources.getString(R.string.err_msg_enter_valid_phone), true)
                false
            }


            else -> {
                true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun handleViews() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE

        binding.btnSave.setOnClickListener {
            if (validatePersonalInfo()) {
                val firstName = binding.firstNameEditText.text.toString()
                val middleName = binding.middleNameEditText.text.toString()
                val lastName = binding.lastNameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                val birthDate = binding.birthdateEditText.text.toString()
                val phoneNumber = binding.phoneEditText.text.toString()
                val countryCode = (binding.spinnerCountryCode.selectedItem as Country).phoneCode
                val countryId = (binding.stateSpinner.selectedItem as Country).id
                val imageId = binding.viewProfileSection.profilePicture.id
                val imageType =
                    binding.viewProfileSection.profilePicture.receiveContentMimeTypes.toString()
                val imagePath = binding.viewProfileSection.profilePicture.id.toString()
                val imageTitle = binding.viewProfileSection.profilePicture.id.toString()

                personalInfoVM.processIntent(
                    PersonalInfoContract.PersonalInfoAction.UpdateUser(
                        firstName,
                        middleName,
                        lastName,
                        email,
                        birthDate,
                        phoneNumber,
                        countryCode,
                        countryId,
//                        imageId= null,
//                        imageType= null,
//                        imagePath= null,
//                        imageTitle=null
                    )
                )
            }
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.birthdateEditText.setOnClickListener {
            showDatePickerDialog(Calendar.getInstance())
        }
        binding.moreButton?.setOnClickListener {
            findNavController().navigate(R.id.action_personalInfoFragment_to_gotoDeleteAccountFragment)
        }
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        personalInfoVM.processIntent(PersonalInfoContract.PersonalInfoAction.GetCountriesFromLocal)
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(personalInfoVM.viewState) {
            renderState(it)
        }
        handleEvents()
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(personalInfoVM.singleEvent) {
            when (it) {
                is PersonalInfoContract.PersonalInfoEvent.CountriesIndex -> {
                    handleSpinnerAdapters(it)
                }

                is PersonalInfoContract.PersonalInfoEvent.UpdateDoneSuccessfully -> showSnackBar(
                    "Your Profile Updated Successfully!",
                    false
                )

                is PersonalInfoContract.PersonalInfoEvent.UpdateFailed -> showSnackBar(
                    it.message,
                    true
                )
            }
        }
    }

    private fun showDatePickerDialog(calendar: Calendar) {
        context?.let { context ->
            val datePickerDialog = DatePickerDialog(context)
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = String.format("%02d-%02d-%d", dayOfMonth, monthOfYear + 1, year)
                binding.birthdateEditText.setText(selectedDate)
                calendar.set(year, monthOfYear, dayOfMonth)
                // To ignore time
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }

            val displayDateFormatter =
                SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH) // Set format and locale
            datePickerDialog.updateDate(currentYear, currentMonth, currentDay)

            // Override the DatePicker's onDateChanged listener for custom formatting
                datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val formattedDate = displayDateFormatter.format(calendar.apply {
                        set(year, monthOfYear, dayOfMonth)
                    })
                    binding.birthdateEditText.setText(formattedDate)
                }

            datePickerDialog.show()
        }
    }


    private fun renderState(state: PersonalInfoContract.PersonalInfoState) {
        CoroutineScope(Dispatchers.Main).launch {
            if (state.isLoading) {
                showLoading(resources.getString(R.string.please_wait))
            } else {
                hideLoading()
            }
            state.exception?.let {
                Toast.makeText(
                    requireContext(),
                    it.message ?: "Unexpected Error",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun handleSpinnerAdapters(it: PersonalInfoContract.PersonalInfoEvent.CountriesIndex) {
        val spinnerAdapter = CountriesSpinnerAdapter(requireContext(), it.countries)
        val spinnerCountryCode = CountryCodeSpinnerAdapter(requireContext(), it.countries)
        binding.stateSpinner.adapter = spinnerAdapter
        binding.spinnerCountryCode.adapter = spinnerCountryCode
    }
}