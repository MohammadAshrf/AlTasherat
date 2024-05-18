package com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginFragment
import com.solutionplus.altasherat.features.signup.presentation.ui.SignupFragment

class ViewPagerAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val loginFragment: LoginFragment,
    private val signupFragment: SignupFragment
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> loginFragment
            1 -> signupFragment
            else -> throw LeonException.Client.Unhandled(httpErrorCode = 400, message = "Invalid tab position: $position")
        }
    }
}