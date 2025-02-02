package com.solutionplus.altasherat.features.personalInfo.presentation.ui

import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentPersonalInfoBinding
import com.solutionplus.altasherat.features.personalInfo.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetCountriesLocal
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedProfileLocal
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.GetUpdatedProfileRemote
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoAction.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.presentation.ui.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.services.country.adapters.CountriesSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.adapters.CountryCodeSpinnerAdapter
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint
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

    override fun viewInit() {
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        personalInfoVM.processIntent(GetCountriesLocal)
    }

    override fun subscribeToObservables() {
        listeners()
        renderState()
        handleEvents()
    }

    private fun listeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            personalInfoVM.processIntent(GetUpdatedProfileRemote)
            binding.swipeRefreshLayout.isRefreshing = false
            binding.viewProfileSection.outerCircle.setImageDrawable(R.drawable.outer_circle.let
            {
                ResourcesCompat.getDrawable(resources, it, null)
            })
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
        collectFlowWithLifecycle(personalInfoVM.singleEvent) {
            when (it) {
                is PersonalInfoEvent.UpdateProfileSuccess -> {
                    showSnackBar(
                        getString(R.string.profile_updated_successfully),
                        false
                    )
                    findNavController().popBackStack()
                }

                is PersonalInfoEvent.UpdateProfileFailed -> showSnackBar(
                    it.message,
                    true
                )

                is PersonalInfoEvent.GetUpdatedProfileRemote -> handleUserInfo(it.user)

                is PersonalInfoEvent.GetCountriesLocal -> {
                    countriesList = it.countries
                    personalInfoVM.processIntent(GetUpdatedProfileLocal)
                    val spinnerAdapter = CountriesSpinnerAdapter(requireContext(), it.countries)
                    binding.stateSpinner.adapter = spinnerAdapter

                    binding.spinnerCountryCode.adapter = CountryCodeSpinnerAdapter(
                        requireContext(),
                        it.countries
                    )
                    binding.stateSpinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                binding.stateSpinner.adapter.getItem(position) as Country
                                binding.spinnerCountryCode.adapter.getItem(position) as Country
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }
                }

                is PersonalInfoEvent.GetUpdatedProfileLocal -> handleUserInfo(it.user)
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
        collectFlowWithLifecycle(personalInfoVM.viewState) { it ->
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

    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val tempFile = File(context.cacheDir, getString(R.string.cached_image))
        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return tempFile
    }

    private fun showDatePickerDialog(calendar: Calendar) {
        context?.let { context ->
            val datePickerDialog = DatePickerDialog(context)
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            datePickerDialog.setOnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate =
                    String.format(getString(R.string.date_format), year, monthOfYear + 1, dayOfMonth)
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

    private fun handleValidationErrors(errors: Map<String, Any>, getErrorMessage: (Any) -> String) {
        errors[Validation.FIRST_NAME]?.let {
            binding.firstNameEditText.error = getErrorMessage(it)
            clearErrorAfterChanged(binding.firstNameEditText)
        }
        errors[Validation.MIDDLE_NAME]?.let {
            binding.middleNameEditText.error = getErrorMessage(it)
            clearErrorAfterChanged(binding.middleNameEditText)
        }
        errors[Validation.LAST_NAME]?.let {
            binding.lastNameEditText.error = getErrorMessage(it)
            clearErrorAfterChanged(binding.lastNameEditText)
        }
        errors[Validation.EMAIL]?.let {
            binding.emailEditText.error = getErrorMessage(it)
            clearErrorAfterChanged(binding.emailEditText)
        }
        errors[Validation.PHONE]?.let {
            binding.phoneEditText.error = getErrorMessage(it)
            clearErrorAfterChanged(binding.phoneEditText)
        }
        errors[Validation.IMAGE]?.let {
            binding.viewProfileSection.outerCircle.setImageDrawable(
                R.drawable.outer_red_circle.let {
                    ResourcesCompat.getDrawable(resources, it, null)
                }
            )
            showSnackBar(getString(R.string.invalid_image), false)
        }
        errors[Validation.BIRTH_DATE]?.let {
            binding.birthdateEditText.error = getErrorMessage(it)
            clearErrorAfterChanged(binding.birthdateEditText)
            showSnackBar(getString(R.string.invalid_birth_date), false)
        }
    }

    private fun clearErrorAfterChanged(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Clear the error message when the text changes
                editText.error = null
                TextInputLayout.END_ICON_NONE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}