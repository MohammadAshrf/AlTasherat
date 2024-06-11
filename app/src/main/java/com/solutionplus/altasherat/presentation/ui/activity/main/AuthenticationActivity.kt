package com.solutionplus.altasherat.presentation.ui.activity.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding>() {
    override fun viewInit() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.AuthunticationContainerView.id) as NavHostFragment
        navHostFragment.findNavController()
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {}
}