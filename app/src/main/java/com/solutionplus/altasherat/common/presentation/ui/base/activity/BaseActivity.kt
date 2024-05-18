package com.solutionplus.altasherat.common.presentation.ui.base.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.android.extentions.bindView
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.ErrorHandling

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity(), ErrorHandling {

    private lateinit var _binding: Binding
    protected val binding: Binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = bindView()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewInit()
        onActivityReady(savedInstanceState)

    }

    abstract fun viewInit()
    abstract fun onActivityReady(savedInstanceState: Bundle?)
    override fun handleHttpExceptions(exception: LeonException) {
        when (exception) {
            is LeonException.Client.Unauthorized -> {
                //navigate to Home
            }

            is LeonException.Local.IOOperation -> TODO()
            is LeonException.Network.Retrial -> TODO()

            is LeonException.Client.ResponseValidation -> TODO()
            is LeonException.Local.RequestValidation -> TODO()

            is LeonException.Network.Unhandled -> showToasts("Unhandled Network Error")
            is LeonException.Client.Unhandled -> showToasts("Unhandled Client Error")
            is LeonException.Server.InternalServerError -> showToasts("Internal Server Error")
            is LeonException.Unknown -> showToasts("Unknown Error")
        }
    }

    private fun showToasts(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

