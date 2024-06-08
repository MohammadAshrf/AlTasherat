package com.solutionplus.altasherat.presentation.ui.fragment.dashboard

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.ButtomSheetLayoutBinding
import com.solutionplus.altasherat.databinding.FragmentVisaPlatformBinding
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginViewModel
import com.solutionplus.altasherat.features.profileMenu.Presentation.ProfileMenuViewModel
import com.solutionplus.altasherat.features.profileMenu.Presentation.fragment.ProfileMenuFragment
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract
import com.solutionplus.altasherat.presentation.ui.activity.main.AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class VisaPlatformFragment : BaseFragment<FragmentVisaPlatformBinding>() {

    private val viewModel: VisaPlatformVM by viewModels<VisaPlatformVM>()


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.IsUserLoggedIn)
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) {
            when (it) {
                is VisaPlatformContract.VisaPlatformEvent.IsUserLoggedIn -> {
                    if (it.isUserLoggedIn) {
                        binding.apply {
                            button.setOnClickListener { showToast() }
                            button2.setOnClickListener { showToast() }
                            button3.setOnClickListener { showToast() }
                            button4.setOnClickListener { showToast() }
                            button5.setOnClickListener { showToast() }
                            button6.setOnClickListener { showToast() }
                        }
                    } else {
                        binding.apply {
                            button.setOnClickListener { handleButtonClick() }
                            button2.setOnClickListener { handleButtonClick() }
                            button3.setOnClickListener { handleButtonClick() }
                            button4.setOnClickListener { handleButtonClick() }
                            button5.setOnClickListener { handleButtonClick() }
                            button6.setOnClickListener { handleButtonClick() }
                        }
                    }
                }

            }
        }

        collectFlowWithLifecycle(viewModel.viewState) { state ->
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

    private fun showToast(){
        Toast.makeText(requireContext(), "the user is logged in go to the next", Toast.LENGTH_SHORT).show()

    }
    override fun viewInit() {

    }

    private fun handleButtonClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val binding = ButtomSheetLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)
        binding.btnLoginAndSignup.setOnClickListener {
            navigateToNextFragment()
        }
        bottomSheetDialog.show()
    }

    private fun navigateToNextFragment() {
        val intent = Intent(requireActivity() , AuthenticationActivity::class.java)
        startActivity(intent)
    }
}