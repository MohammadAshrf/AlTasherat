package com.solutionplus.altasherat.features.resetPassword.presentation


import android.content.Intent
import android.os.Bundle
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentEnterNewPasswordBinding
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity


class EnterNewPasswordFragment : BaseFragment<FragmentEnterNewPasswordBinding>() {
    override fun onFragmentReady(savedInstanceState: Bundle?) {
    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {
     binding.btnEnterNewPassword.setOnClickListener {
         val intent = Intent(activity, HomeActivity::class.java)
         startActivity(intent)
     }
    }

}