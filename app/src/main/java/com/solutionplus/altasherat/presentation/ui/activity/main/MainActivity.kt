package com.solutionplus.altasherat

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun viewInit() {
//        binding.textHello.text = "Welcome to Altasherat"
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as? NavHostFragment
            navHostFragment?.navController

        logger.debug("onActivityReady")
    }

    companion object {
        private val logger = getClassLogger()
    }
}