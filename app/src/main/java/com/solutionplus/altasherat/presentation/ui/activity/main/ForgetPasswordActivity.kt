package com.solutionplus.altasherat.presentation.ui.activity.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityForgetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordActivity : BaseActivity<ActivityForgetPasswordBinding>() {
    override fun viewInit() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView2.id) as? NavHostFragment
        navHostFragment?.navController
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

}