package com.solutionplus.altasherat.features.profileMenu.Presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentEmailVerifiedBinding

class EmailVerifiedFragment : BaseFragment<FragmentEmailVerifiedBinding>() {
    override fun onFragmentReady(savedInstanceState: Bundle?) {


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {

    }

}