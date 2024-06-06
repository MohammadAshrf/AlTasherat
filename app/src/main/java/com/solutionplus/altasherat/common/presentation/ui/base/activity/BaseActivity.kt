package com.solutionplus.altasherat.common.presentation.ui.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.solutionplus.altasherat.android.helpers.extentions.bindView
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.ErrorHandling
import com.solutionplus.altasherat.presentation.ui.activity.main.AuthenticationActivity

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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
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
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
            }

            is LeonException.Local.IOOperation -> TODO()
            is LeonException.Network.Retrial -> TODO()

            is LeonException.Client.ResponseValidation -> {
                //showSnackbar(exception.message ?: "Unknown validation error")
            }

            is LeonException.Local.RequestValidation -> {
                logger.error(exception.message)
                //showSnackbar( exception.message ?: "Unknown validation error")
            }

            is LeonException.Network.Unhandled -> showSnackbar("Unhandled Network Error")
            is LeonException.Client.Unhandled -> showSnackbar("Unhandled Client Error")
            is LeonException.Server.InternalServerError -> showSnackbar("Internal Server Error")
            is LeonException.Unknown -> showSnackbar("Unknown Error")
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        val logger = getClassLogger()
    }
}

