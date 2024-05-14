package com.solutionplus.altasherat.common.presintaion


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.solutionx.common.presentaion.bindView

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity() {
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



}

