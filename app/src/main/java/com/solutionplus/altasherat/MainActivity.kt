package com.solutionplus.altasherat

<<<<<<< HEAD
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
=======
import am.leon.solutionx.android.helpers.logging.getClassLogger
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.solutionplus.altasherat.common.presintaion.BaseActivity
import com.solutionplus.altasherat.databinding.ActivityMainBinding
>>>>>>> cfb1dac1eda7749c5779221ceab1a6dc04d7939a
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
<<<<<<< HEAD
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
=======
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingClass = ActivityMainBinding::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

>>>>>>> cfb1dac1eda7749c5779221ceab1a6dc04d7939a
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
<<<<<<< HEAD
=======


    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
        logger.info("onActivityReady")
    }

    override fun viewInit() {

    }


    companion object {
        private val logger = getClassLogger()
>>>>>>> cfb1dac1eda7749c5779221ceab1a6dc04d7939a
    }
}