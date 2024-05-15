package com.solutionplus.altasherat

import am.leon.solutionx.android.helpers.logging.getClassLogger
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.solutionplus.altasherat.common.presentation.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingClass = ActivityMainBinding::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
        logger.info("onActivityReady")
    }

    override fun viewInit() {

    }


    companion object {
        private val logger = getClassLogger()
    }
}