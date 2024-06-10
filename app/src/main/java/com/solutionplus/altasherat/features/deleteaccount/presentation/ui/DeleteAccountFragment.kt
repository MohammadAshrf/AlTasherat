package com.solutionplus.altasherat.features.deleteaccount.presentation.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.DeleteAccountButtomSheetBinding
import com.solutionplus.altasherat.databinding.FragmentDeleteAccountBinding
import com.solutionplus.altasherat.databinding.FragmentProfileMenuBinding
import com.solutionplus.altasherat.presentation.ui.activity.main.AuthenticationActivity
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeleteAccountFragment : BaseFragment<FragmentDeleteAccountBinding>() {

    private val viewModel: DeleteAccountVM by viewModels()
    private lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var bindingBottomSheet: DeleteAccountButtomSheetBinding
    override fun onFragmentReady(savedInstanceState: Bundle?) {

    }

    override fun subscribeToObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.viewState.collect { state ->
                        getClassLogger().info(state.exception.toString())

                        state.exception?.let {
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
                        if (state.isLoading) {
                            showLoading()
                        } else {
                            hideLoading()
                        }
                    }
                }
                launch {
                    viewModel.singleEvent.collect { event ->
                        handleEvent(event)
                    }
                }
            }
        }
    }

    override fun viewInit() {
        binding.btnCancellation.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnShowBottomSheet.setOnClickListener {
            handleButtonClick()
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handleEvent(event: DeleteAccountContract.DeleteAccountEvents) {
        when (event) {
            is DeleteAccountContract.DeleteAccountEvents.DeleteAccountSuccess -> {
                showSnackBar("Your Account is deleted successfully", false)
                requireActivity().finish()
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun handleButtonClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bindingBottomSheet = DeleteAccountButtomSheetBinding.inflate(layoutInflater)
        bindingBottomSheet.btnDelete.setOnClickListener {
            val password = bindingBottomSheet.etReTypeNewPassword.text.toString()
            viewModel.onActionTrigger(
                DeleteAccountContract.DeleteAccountActions.DeleteAccount(
                    password,
                )
            )

        }
        bindingBottomSheet.btnCancellation.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(bindingBottomSheet.root)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.show()

    }

    private fun handleValidationErrors(errors: Map<String, Any>, getErrorMessage: (Any) -> String) {
        errors[Validation.PASSWORD]?.let {
            bindingBottomSheet.etReTypeNewPassword.error = getErrorMessage(it)
            bindingBottomSheet.textInputLayout4.endIconMode = TextInputLayout.END_ICON_NONE
            bindingBottomSheet.etReTypeNewPassword.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    bindingBottomSheet.textInputLayout4.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    bindingBottomSheet.etReTypeNewPassword.error = null
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}