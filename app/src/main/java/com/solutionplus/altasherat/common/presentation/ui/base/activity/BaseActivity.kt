package com.solutionplus.altasherat.common.presentation.ui.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.solutionplus.altasherat.R
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
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
            }

            is LeonException.Local.IOOperation -> {}
            is LeonException.Network.Retrial -> {}

            is LeonException.Client.ResponseValidation -> {}

            is LeonException.Local.RequestValidation -> {
                logger.error(exception.message)
            }

            is LeonException.Network.Unhandled -> showSnackBar(getString(R.string.unhandled_network_error))
            is LeonException.Client.Unhandled -> showSnackBar(getString(R.string.unhandled_client_error))
            is LeonException.Server.InternalServerError -> showSnackBar(getString(R.string.internal_server_error))
            is LeonException.Unknown -> showSnackBar(getString(R.string.unknown_error))
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        val logger = getClassLogger()
    }
}

