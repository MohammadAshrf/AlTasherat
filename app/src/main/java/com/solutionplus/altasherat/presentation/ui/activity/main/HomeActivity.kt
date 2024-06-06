package com.solutionplus.altasherat.presentation.ui.activity.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun viewInit() {
        val host = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        setupWithNavController(binding.bottomNav, host.navController)


        setupBottomNavigation(host)
    }

    private fun setupBottomNavigation(host: NavHostFragment) {
        // Get the NavController
        val navController = host.navController

        // Add an OnDestinationChangedListener
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.visaPlatformFragment, R.id.visaApplicationsFragment, R.id.profileMenuFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNav.visibility = View.GONE
                }
            }
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigationView) { view, insets ->
            insets
        }
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {

    }

}