package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import com.solutionplus.altasherat.features.personalInfo.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetCountriesFromLocal
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedUserFromLocal
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedUserFromRemote
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.services.country.adapters.CountriesSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Locale

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding>() {
    private lateinit var countriesList: List<Country>
    private var imageUri: Uri? = null
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            imageUri = uri
            binding.viewProfileSection.profilePicture.setImageURI(uri)
        }
    }
    private val personalInfoVM: PersonalInfoViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun viewInit() {
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        personalInfoVM.processIntent(GetCountriesFromLocal)
    }

    override fun subscribeToObservables() {
        listeners()
        renderState()
        handleEvents()
    }

    private fun listeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            personalInfoVM.processIntent(GetUpdatedUserFromRemote)
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.moreButton.setOnClickListener {
            findNavController().navigate(R.id.action_personalInfoFragment_to_gotoDeleteAccountFragment)
        }
        binding.btnSave.setOnClickListener {
            val firstname = binding.firstNameEditText.text.toString().trim()
            val middleName = binding.middleNameEditText.text.toString().trim()
            val lastname = binding.lastNameEditText.text.toString().trim()
            val phone = PhoneRequest(
                countryCode = countriesList[binding.spinnerCountryCode.selectedItemPosition].phoneCode,
                number = binding.phoneEditText.text.toString()
            )
            val email = binding.emailEditText.text.toString().trim()

            val image = imageUri?.let {
                uriToFile(it, requireContext())  // Convert Uri to File only if imageUri is not null
            }

            val birthdate = binding.birthdateEditText.text.toString()
            val country = countriesList[binding.stateSpinner.selectedItemPosition].id
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
        binding.viewProfileSection.penEditor.setOnClickListener {
            contract.launch("image/*")
        }
    }



    private fun handleEvents() {
        collectFlowWithLifecycle(personalInfoVM.singleEvent) { it ->
            when (it) {
                is PersonalInfoEvent.UpdateDoneSuccessfully -> {
                    showSnackBar(
                        "Your Profile Updated Successfully!",
                        false
                    )
                    findNavController().popBackStack()
                }

                is PersonalInfoEvent.UpdateFailed -> showSnackBar(
                    it.message,
                    true
                )

                is PersonalInfoEvent.GetUpdatedUserFromRemote -> handleUserInfo(it.user)

                is PersonalInfoEvent.GetCountriesFromLocal -> {
                    countriesList = it.countries
                    personalInfoVM.processIntent(GetUpdatedUserFromLocal)
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

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }
                }

                is PersonalInfoEvent.GetUpdatedUserFromLocal -> handleUserInfo(it.user)
            }
        }
    }

    private fun handleUserInfo(it: User) {
        binding.firstNameEditText.setText(it.firstname)
        binding.middleNameEditText.setText(it.middleName)
        binding.lastNameEditText.setText(it.lastname)
        binding.phoneEditText.setText(it.phone.number)
        binding.spinnerCountryCode.setSelection(countriesList.indexOf(countriesList.find { country -> country.phoneCode == it.phone.countryCode }))
        binding.emailEditText.setText(it.email)
        it.image.let { image ->
            Glide.with(this)
                .load(image.path)
                .centerCrop()
                .placeholder(R.drawable.ic_profile_holder)
                .error(R.drawable.ic_profile_holder)
                .into(binding.viewProfileSection.profilePicture)
        }
        binding.birthdateEditText.setText(it.birthdate)
        binding.stateSpinner.setSelection(countriesList.indexOf(countriesList.find { country -> country.id == it.country.id }))
    }

    private fun renderState() {
        collectFlowWithLifecycle(personalInfoVM.viewState) {
            CoroutineScope(Dispatchers.Main).launch {
                if (it.isLoading) {
                    showLoading(resources.getString(R.string.please_wait))
                } else {
                    hideLoading()
                }
                it.exception?.let {
                    if (it is LeonException.Local.RequestValidation) {
                        val errorMessages = it.errors
                        errorMessages[Validation.FIRST_NAME]?.let { binding.firstNameEditText.error = getString(it) }
                        errorMessages[Validation.MIDDLE_NAME]?.let { binding.middleNameEditText.error = getString(it) }
                        errorMessages[Validation.LAST_NAME]?.let { binding.lastNameEditText.error = getString(it) }
                        errorMessages[Validation.EMAIL]?.let { binding.emailEditText.error = getString(it) }
                        errorMessages[Validation.PHONE]?.let { binding.phoneEditText.error = getString(it) }
                        errorMessages[Validation.COUNTRY]?.let { binding.country.error = getString(it) }
                    }

                    if (it is LeonException.Client.ResponseValidation) {
                        val errorMessages = it.errors
                        errorMessages[Validation.FIRST_NAME]?.let { binding.firstNameEditText.error = it }
                        errorMessages[Validation.MIDDLE_NAME]?.let { binding.middleNameEditText.error = it
                            errorMessages[Validation.LAST_NAME]?.let { binding.lastNameEditText.error = it }
                            errorMessages[Validation.EMAIL]?.let { binding.emailEditText.error = it }
                            errorMessages[Validation.PHONE]?.let { binding.phoneEditText.error = it }
                            errorMessages[Validation.COUNTRY]?.let { binding.country.error =it }
                        }
                    }
                }
            }
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val tempFile = File(context.cacheDir, "temp_image")
        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return tempFile
    }

    @SuppressLint("DefaultLocale")
    private fun showDatePickerDialog(calendar: Calendar) {
        context?.let { context ->
            val datePickerDialog = DatePickerDialog(context)
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate =
                    String.format("%02d-%02d-%d", year, monthOfYear + 1, dayOfMonth)
                binding.birthdateEditText.setText(selectedDate)
                calendar.set(year, monthOfYear, dayOfMonth)
                // To ignore time
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }

            val displayDateFormatter =
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH) // Set format and locale
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


}