package com.solutionplus.altasherat.common.presentation.ui.base.frgment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.extentions.bindView
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.ErrorHandling
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.InternetConnectionDelegate
import com.solutionplus.altasherat.common.presentation.ui.base.delegation.InternetConnectionDelegateImpl
import com.solutionplus.altasherat.databinding.ViewLoadingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private var mProgressDialog: Dialog? = null

    fun showLoading(message: String) {
        if (mProgressDialog == null) {
            mProgressDialog = Dialog(requireActivity()).apply {
                setContentView(R.layout.view_loading)
                setCancelable(false)
                setCanceledOnTouchOutside(false)
            }
        }
        mProgressDialog?.findViewById<TextView>(R.id.tv_progress_text)?.text = message
        mProgressDialog?.show()
    }

    fun hideLoading() {
        mProgressDialog?.dismiss()
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

    fun showAlertDialog(@StringRes titleRes: Int, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(titleRes)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    protected fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.Error
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.Accent
                )
            )
        }
        snackBar.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mProgressDialog?.dismiss()
        mProgressDialog = null

    }
}