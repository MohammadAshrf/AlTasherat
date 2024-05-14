package com.solutionplus.altasherat.common.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.viewbinding.ViewBinding
import com.example.solutionx.common.presentaion.bindView
import com.solutionplus.altasherat.common.data.models.exception.SolutionXException


abstract class BaseFragment<Binding : ViewBinding> : Fragment() {

    protected abstract val bindingClass: Class<Binding>

    private var _binding: Binding? = null

    protected val binding: Binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingClass.bindView(this, container)



        return binding.root

    }










}