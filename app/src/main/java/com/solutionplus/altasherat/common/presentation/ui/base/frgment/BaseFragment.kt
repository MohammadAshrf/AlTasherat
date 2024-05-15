package com.solutionplus.altasherat.common.presentation.ui.base.frgment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import androidx.viewbinding.ViewBinding
import com.solutionplus.altasherat.android.extentions.bindView
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.ErrorHandling
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.InternetConnectionDelegate
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.InternetConnectionDelegateImpl
import com.solutionplus.altasherat.databinding.ViewLoadingBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<Binding : ViewBinding> : Fragment(),
    InternetConnectionDelegate by InternetConnectionDelegateImpl(), ErrorHandling {

    private var _binding: Binding? = null
    protected val binding: Binding
        get() = _binding!!

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
        _binding = bindView()

        loadingView = ViewLoadingBinding.inflate(
            inflater,
            binding.root as ViewGroup,
            true
        ) // Inflate loading view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewInit()
        onFragmentReady(savedInstanceState)
        subscribeToObservables()
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

    abstract fun onFragmentReady(savedInstanceState: Bundle?)

    abstract fun subscribeToObservables()

    abstract fun viewInit()

    protected fun isInternetAvailable(): Boolean {
        return isInternetAvailable(requireContext())
    }

    protected fun <T> collectFlowWithLifecycle(flow: Flow<T>, action: (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest {
                    action(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}