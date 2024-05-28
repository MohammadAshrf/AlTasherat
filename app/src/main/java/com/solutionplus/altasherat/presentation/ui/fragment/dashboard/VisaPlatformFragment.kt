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
import com.solutionplus.altasherat.databinding.FragmentVisaPlatformBinding
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginViewModel
import com.solutionplus.altasherat.presentation.ui.activity.main.AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class VisaPlatformFragment : Fragment() {

    private lateinit var binding: FragmentVisaPlatformBinding
    val controller: NavController by lazy { findNavController() }
    private lateinit var bottomSheetDialog: BottomSheetDialog

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
        binding.button4.setOnClickListener {
            findNavController().navigate(R.id.action_visaPlatformFragment_to_gotoDeleteAccountFragment)
        }
        binding.button5.setOnClickListener { handleButtonClick() }
        binding.button6.setOnClickListener { handleButtonClick() }
    }

    private fun handleButtonClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            showBottomSheetDialog()
        }
    }

    private fun navigateToNextFragment() {
        val intent = Intent(requireActivity() , AuthenticationActivity::class.java)
        startActivity(intent)
    }

    private fun showBottomSheetDialog() {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.buttom_sheet_layout, null)
        val btnClose = view.findViewById<Button>(R.id.btnLoginAndSignup)
        btnClose.setOnClickListener {
            navigateToNextFragment()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.show()

        bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                bottomSheetDialog.dismiss()
            }
            false
        }
    }
}