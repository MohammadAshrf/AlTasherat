package com.solutionplus.altasherat.features.deleteaccount.presentation.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
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
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.VISIBLE
    }



    override fun subscribeToObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.viewState.collect { state ->
                        getClassLogger().info(state.exception.toString())

                        state.exception?.let {
                            handleHttpExceptions(it)
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
                val intent = Intent(requireActivity() , HomeActivity::class.java)
                startActivity(intent)
            }
            is DeleteAccountContract.DeleteAccountEvents.DeleteAccountError -> {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
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
            val password = bindingBottomSheet.etPassword.text.toString()
            viewModel.onActionTrigger(
                DeleteAccountContract.DeleteAccountActions.DeleteAccount(
                    password,
                )
            )
            bottomSheetDialog.dismiss()
        }
        bindingBottomSheet.btnCancellation.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(bindingBottomSheet.root)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.show()

    }
}