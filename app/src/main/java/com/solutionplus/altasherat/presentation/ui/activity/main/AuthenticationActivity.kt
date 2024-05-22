package com.solutionplus.altasherat.presentation.ui.activity.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding>() {
    override fun viewInit() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.AuthunticationContainerView.id) as NavHostFragment
        val navController = navHostFragment.findNavController()
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {

    }

}