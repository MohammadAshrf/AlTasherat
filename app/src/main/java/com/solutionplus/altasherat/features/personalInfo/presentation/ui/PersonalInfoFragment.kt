package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import com.solutionplus.altasherat.features.personalInfo.data.models.request.CountryRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.request.ImageRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetCountriesFromLocal
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedUserFromLocal
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedUserFromRemote
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoState
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
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var countriesList: List<Country>
    private val personalInfoVM: PersonalInfoViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun viewInit() {
        handleViews()
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        personalInfoVM.processIntent(GetCountriesFromLocal)
         personalInfoVM.processIntent(GetUpdatedUserFromLocal)
        binding.swipeRefreshLayout.setOnRefreshListener {
            personalInfoVM.processIntent(GetUpdatedUserFromRemote)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(personalInfoVM.viewState) {
            renderState(it)
        }
        handleEvents()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun handleViews() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE


        binding.moreButton.setOnClickListener {
        }

        binding.btnSave.setOnClickListener {
            val firstname = binding.firstNameEditText.text.toString()
            val middleName = binding.middleNameEditText.text.toString()
            val lastname = binding.lastNameEditText.text.toString()
            val phone = PhoneRequest(
                (binding.phoneEditText.text.toString()),
                binding.spinnerCountryCode.selectedItem.toString()
            )
            val email = binding.emailEditText.text.toString()
            val image = binding.viewProfileSection.profilePicture.drawable.toBitmap().let {
                ImageRequest(it.generationId, "image/jpeg", "image", "image")
            }
            val birthdate = binding.birthdateEditText.text.toString()
            val country = binding.stateSpinner.adapter.getItem(0) as CountryRequest

            personalInfoVM.processIntent(
                UpdateUser(
                    firstname,
                    middleName,
                    lastname,
                    email,
                    phone,
                    image,
                    birthdate,
                    country
                )
            )
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.birthdateEditText.setOnClickListener {
            showDatePickerDialog(Calendar.getInstance())
        }

        // Register the activity result launcher
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val uri = result.data?.data
                    if (uri != null) {
                        Glide.with(this)
                            .load(uri)
                            .centerCrop()
                            .placeholder(R.drawable.image_plane) // Optional placeholder image
                            .error(R.drawable.ic_verefication) // Optional error image
                            .into(binding.viewProfileSection.profilePicture)
                    }
                }
            }

        binding.viewProfileSection.profilePicture.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            galleryLauncher.launch(intent)
        }
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(personalInfoVM.singleEvent) {
            when (it) {
                is PersonalInfoEvent.UpdateDoneSuccessfully -> showSnackBar(
                    "Your Profile Updated Successfully!",
                    false
                )

                is PersonalInfoEvent.UpdateFailed -> showSnackBar(
                    "${it.message} fetching info",
                    true
                )

                is PersonalInfoEvent.GetUpdatedUserFromRemote -> handleRemoteInfo(it.user)

                is PersonalInfoEvent.GetCountriesFromLocal -> {
                    countriesList = it.countries
                    logger.info("countries: ${it.countries}")
                    val spinnerAdapter = CountriesSpinnerAdapter(requireContext(), it.countries)
                    binding.stateSpinner.adapter = spinnerAdapter

                    binding.spinnerCountryCode.adapter = CountryCodeSpinnerAdapter(
                        requireContext(),
                        it.countries
                    )
                    binding.stateSpinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                binding.stateSpinner.adapter.getItem(position) as Country
                                binding.spinnerCountryCode.adapter.getItem(position) as Country
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        }


                }

                is PersonalInfoEvent.GetUpdatedUserFromLocal -> handleLocalInfo(it.user)
            }
        }
    }

    private fun handleLocalInfo(it: User) {
        binding.firstNameEditText.setText(it.firstname)
        binding.middleNameEditText.setText(it.middleName)
        binding.lastNameEditText.setText(it.lastname)
        binding.phoneEditText.setText(it.phone.number)
        binding.spinnerCountryCode.setSelection(countriesList.indexOf(countriesList.find { country -> country.phoneCode == it.phone.countryCode }))
        binding.emailEditText.setText(it.email)
        binding.birthdateEditText.setText(it.birthdate)
        binding.stateSpinner.setSelection(countriesList.indexOf(countriesList.find { country -> country.id == it.country.id }))
    }

    private fun handleRemoteInfo(it: User) {
        binding.firstNameEditText.setText(it.firstname)
        binding.middleNameEditText.setText(it.middleName)
        binding.lastNameEditText.setText(it.lastname)
        binding.spinnerCountryCode.setSelection(countriesList.indexOf(countriesList.find { country -> country.phoneCode == it.phone.countryCode }))
        binding.phoneEditText.setText(it.phone.number)
        binding.emailEditText.setText(it.email)
        binding.birthdateEditText.setText(it.birthdate)
        binding.stateSpinner.setSelection(countriesList.indexOf(countriesList.find { country -> country.id == it.country.id }))
    }

    private fun renderState(state: PersonalInfoState) {
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

    private fun showDatePickerDialog(calendar: Calendar) {
        context?.let { context ->
            val datePickerDialog = DatePickerDialog(context)
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate =
                    String.format("%02d-%02d-%d", dayOfMonth, monthOfYear + 1, year)
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

    companion object {
        val logger = getClassLogger()
    }

//    private fun validatePersonalInfo(): Boolean {
//        return when {
//            binding.firstNameEditText.text?.trim()?.length !in 3..15 -> {
//                showSnackBar(
//                    resources.getString(R.string.err_msg_enter_valid_first_name), true
//                )
//                false
//            }
//
//            (binding.middleNameEditText.text?.trim()?.length ?: 0) > 15 -> {
//                showSnackBar(
//                    resources.getString(R.string.err_msg_enter_valid_middle_name), true
//                )
//                false
//            }
//
//            binding.lastNameEditText.text?.trim()?.length !in 3..15 -> {
//                showSnackBar(
//                    resources.getString(R.string.err_msg_enter_valid_last_name), true
//                )
//                false
//            }
//
//            binding.emailEditText.text?.trim()?.length !in 0..50 || !Patterns.EMAIL_ADDRESS.matcher(
//                binding.emailEditText.text.toString()
//            ).matches() -> {
//                showSnackBar(resources.getString(R.string.err_msg_enter_valid_email), true)
//                false
//            }
//
//            binding.phoneEditText.text?.trim()?.length !in 9..15 || !TextUtils.isDigitsOnly(binding.phoneEditText.text.toString()) -> {
//                showSnackBar(resources.getString(R.string.err_msg_enter_valid_phone), true)
//                false
//            }
//
//
//            else -> {
//                true
//            }
//        }
//    }
}
