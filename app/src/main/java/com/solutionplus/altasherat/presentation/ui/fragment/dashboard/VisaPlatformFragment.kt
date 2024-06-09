package com.solutionplus.altasherat.presentation.ui.fragment.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.ButtomSheetLayoutBinding
import com.solutionplus.altasherat.databinding.FragmentVisaPlatformBinding
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

    private fun showToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.you_re_already_logged_in), Toast.LENGTH_SHORT
        ).show()
    }

    override fun viewInit() {

        binding.button.setOnClickListener { handleButtonClick() }
        binding.button2.setOnClickListener { handleButtonClick() }
        binding.button3.setOnClickListener { handleButtonClick() }
        binding.button4.setOnClickListener { handleButtonClick() }
        binding.button5.setOnClickListener { handleButtonClick() }
        binding.button6.setOnClickListener { handleButtonClick() }
    }

    private fun handleButtonClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            showBottomSheetDialog()
        }
    }

    private fun navigateToNextFragment() {
        val intent = Intent(requireActivity(), AuthenticationActivity::class.java)
        startActivity(intent)
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
}