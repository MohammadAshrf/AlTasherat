package com.solutionplus.altasherat.common.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.databinding.ViewLoadingBinding


abstract class BaseFragment<Binding : ViewBinding> : Fragment(), ErrorHandling {

    protected abstract val bindingClass: Class<Binding>

    private var _binding: Binding? = null

    protected val binding: Binding get() = _binding!!

    private lateinit var loadingView: ViewLoadingBinding

    private lateinit var errorHandling: ErrorHandling

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ErrorHandling)
            errorHandling = context
        else
            throw IllegalStateException("Activity must implement ErrorHandling")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingClass.bindView(this, container)

        loadingView = ViewLoadingBinding.inflate(
            inflater,
            binding.root as ViewGroup,
            true
        ) // Inflate loading view


        return binding.root

    }

    override fun handleHttpExceptions(exception: LeonException) {
        if (this::errorHandling.isInitialized) {
            errorHandling.handleHttpExceptions(exception)
        }
    }

    protected fun showLoading() {
        loadingView.loadingView.visibility = View.VISIBLE
    }

    protected fun hideLoading() {
        loadingView.loadingView.visibility = View.GONE
    }


}