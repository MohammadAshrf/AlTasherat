package com.solutionplus.altasherat.presentation.ui.fragment.dashboard

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.FragmentVisaPlatformBinding
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class VisaPlatformFragment : Fragment() {

    private lateinit var binding: FragmentVisaPlatformBinding
    private val viewModel: LoginViewModel by viewModels()
    val controller: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVisaPlatformBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener { handleButtonClick() }
        binding.button2.setOnClickListener { handleButtonClick() }
        binding.button3.setOnClickListener { handleButtonClick() }
        binding.button4.setOnClickListener { handleButtonClick() }
        binding.button5.setOnClickListener { handleButtonClick() }
        binding.button6.setOnClickListener { handleButtonClick() }
    }

    private fun handleButtonClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isUserLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    findNavController().navigate(R.id.action_navigation_visa_platform_to_navigation_visa_applications)
//                    navigateToNextFragment()
                } else {
                    // User is not logged in, show the bottom sheet dialog for login/signup
                    showBottomSheetDialog()
                }
            }
        }
    }

    private fun navigateToNextFragment() {
           val direction =   findNavController().navigate(R.id.action_navigation_visa_platform_to_mainActivity)
//        controller.navigate(direction)
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.buttom_sheet_layout, null)
        val btnClose = view.findViewById<Button>(R.id.btnLoginAndSignup)
        btnClose.setOnClickListener {
            navigateToNextFragment()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}