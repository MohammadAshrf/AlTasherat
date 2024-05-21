package com.solutionplus.altasherat.presentation.ui.activity.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.ErrorHandling
import com.solutionplus.altasherat.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun viewInit() {
        val host =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        setupWithNavController(binding.navView, host.navController)
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
        
    }

}