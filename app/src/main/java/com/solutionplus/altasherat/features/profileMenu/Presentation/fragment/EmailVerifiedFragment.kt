package com.solutionplus.altasherat.features.profileMenu.Presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Constants.EMAIL_KEY_BUNDLE
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentEmailVerifiedBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EmailVerifiedFragment : BaseFragment<FragmentEmailVerifiedBinding>() {
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

    }

    override fun viewInit() {
        val emailText = arguments?.getString(EMAIL_KEY_BUNDLE)
        binding.emailTv.text = emailText
        binding.btnSkip.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}