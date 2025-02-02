package com.solutionplus.altasherat.features.splash.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_WIFI_SETTINGS
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.InternetConnectionDelegate
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.InternetConnectionDelegateImpl
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentSplashBinding
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val internetConnection: InternetConnectionDelegate by lazy {
        InternetConnectionDelegateImpl()
    }
    private var isDialogShown = false // Flag to track dialog display
    private val splashVM: SplashViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            delay(1200) // Simulate internet check delay & Check onBoarding status
            if (!internetConnection.isInternetAvailable(requireContext()) && !isDialogShown) {
                showInternetAlertDialog()
                isDialogShown = true
            } else {
                splashVM.processIntent(SplashContract.SplashAction.IsOnBoardingShown)
            }
        }
    }

    override fun viewInit() {}

    override fun subscribeToObservables() {
        handleEvents()
    }

    private fun showInternetAlertDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_layout, null)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create() // Create the dialog before setting window attributes
        dialog.window?.attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.show()
        val okButton = dialogView.findViewById<MaterialButton>(R.id.dialogOkButton)
        okButton.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(ACTION_WIFI_SETTINGS))
        }
    }

    private fun handleEvents() {
        collectFlowWithLifecycle(splashVM.singleEvent) {
            when (it) {
                is SplashContract.SplashEvent.FetchCountriesFromLocalAndNavigateToLanguage -> navigateToLanguage()
                is SplashContract.SplashEvent.NavigateToHome -> startHomeActivity()
                is SplashContract.SplashEvent.FetchCountriesFromRemote -> navigateToLanguage()
            }
        }
    }

    private fun navigateToLanguage() {
        findNavController().navigate(R.id.action_splashFragment_to_languageFragment)
    }

    private fun startHomeActivity() {
        startActivity(
            Intent(requireContext(), HomeActivity::class.java)
        ).also { requireActivity().finish() }
    }
}