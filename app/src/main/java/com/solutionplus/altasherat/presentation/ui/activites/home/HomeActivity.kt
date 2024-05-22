package com.solutionplus.altasherat.presentation.ui.activites.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.activity.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun viewInit() {
        binding.textWelcomeHome.text = "Welcome Home"
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

}