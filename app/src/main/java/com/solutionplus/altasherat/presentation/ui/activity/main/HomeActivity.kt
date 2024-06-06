package com.solutionplus.altasherat.presentation.ui.activity.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
        binding.bottomNav.itemIconTintList = null
       // customizeBottomNavigationView(binding.bottomNav)
    }
    override fun onActivityReady(savedInstanceState: Bundle?) { }

    private fun customizeBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        val menu = bottomNavigationView.menu
        for (i in 0 until menu.size()) {
            val menuItem = menu.getItem(i)
            val actionView = menuItem.actionView

            val title = actionView?.findViewById<TextView>(R.id.custom_title)
            val icon = actionView?.findViewById<ImageView>(R.id.custom_icon)

            title?.text = menuItem.title
            icon?.setImageDrawable(menuItem.icon)
        }
    }
}