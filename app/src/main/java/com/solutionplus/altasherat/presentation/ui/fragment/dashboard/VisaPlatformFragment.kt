package com.solutionplus.altasherat.presentation.ui.fragment.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.solutionplus.altasherat.databinding.ButtomSheetLayoutBinding
import com.solutionplus.altasherat.databinding.FragmentVisaPlatformBinding
import com.solutionplus.altasherat.presentation.ui.activity.main.AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VisaPlatformFragment : Fragment() {

    private lateinit var binding: FragmentVisaPlatformBinding
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