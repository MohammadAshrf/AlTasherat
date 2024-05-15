package com.solutionplus.altasherat.common.presentation


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.common.data.model.exception.LeonException

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity() ,ErrorHandling {
    protected abstract val bindingClass: Class<Binding>


    private var _binding: Binding? = null
    protected val binding: Binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingClass.bindView(this)

        setContentView(binding.root)

        viewInit()


        onActivityReady(savedInstanceState)

    }

    abstract fun onActivityReady(savedInstanceState: Bundle?)


    abstract fun viewInit()
     override fun handleHttpExceptions(exception: LeonException) {
        when(exception){
            is LeonException.Client.Unauthorized ->{
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

