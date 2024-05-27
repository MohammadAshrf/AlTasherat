package com.solutionplus.altasherat.presentation.ui.fragment.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentViewPagerBinding
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginFragment
import com.solutionplus.altasherat.features.signup.presentation.ui.SignupFragment
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>() {

    private lateinit var adapter: ViewPagerAdapter

    override fun onFragmentReady(savedInstanceState: Bundle?) {}
    override fun subscribeToObservables() {}
    override fun viewInit() {
       actions()
    }

    private fun actions(){
        setupListener()
        setUpRecyclerview()
        setupViewPagerPageChangeCallback()
        setupTabLayoutSelectedListener()
        setupTabLayout()
        updateButtonText(binding.viewPager.currentItem)
        updateImageViewVisibility(binding.viewPager.currentItem)
        updateTabUnderline(binding.viewPager.currentItem)
    }

    private val signupFragment = SignupFragment()
    private val loginFragment = LoginFragment()
    private fun setupListener(){
        binding.btnLoginAndSignup.setOnClickListener {
            when (binding.viewPager.currentItem) {
                0 -> loginFragment.onLoginAction()
                1 -> signupFragment.onSignupAction()
                else -> throw LeonException.Unknown("Unknown tab position: ${binding.viewPager.currentItem}")
            }
        }
        binding.skip.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_homeActivity)
        }
    }

    private fun setUpRecyclerview(){
        val fragmentManager: FragmentManager = childFragmentManager
        adapter = ViewPagerAdapter(fragmentManager, lifecycle, loginFragment, signupFragment)
        binding.viewPager.adapter = adapter
    }

    private fun setupViewPagerPageChangeCallback() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
                updateButtonText(position)
                updateImageViewVisibility(position)
                updateTabUnderline(position)
            }
        })
    }

    private fun setupTabLayoutSelectedListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.setCurrentItem(tab.position, true)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun setupTabLayout() {
        val inflater = LayoutInflater.from(requireContext())
        val tabTitles = arrayOf("تسجيل الدخول", "حساب جديد")
        tabTitles.forEachIndexed { index, title ->
            val tab = binding.tabLayout.newTab()
            val tabView = inflater.inflate(R.layout.custom_tab_with_underline, null)
            tab.customView = tabView
            tabView.findViewById<TextView>(R.id.tab_text).text = title
            binding.tabLayout.addTab(tab)
        }
    }

    private fun updateButtonText(position: Int) {
        when (position) {
            0 -> binding.btnLoginAndSignup.text = "تسجيل الدخول"
            1 -> binding.btnLoginAndSignup.text = "حساب جديد"
        }
    }

    private fun updateImageViewVisibility(position: Int) {
        when (position) {
            0 -> {
                binding.imageView5.visibility = View.VISIBLE
                binding.imageViewp2.visibility = View.GONE
            }
            1 -> {
                binding.imageView5.visibility = View.GONE
                binding.imageViewp2.visibility = View.VISIBLE
            }
        }
    }

    private fun updateTabUnderline(position: Int) {
        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = binding.tabLayout.getTabAt(i)
            val underlineView = tab?.customView?.findViewById<View>(R.id.underline_view)
            underlineView?.visibility = if (i == position) View.VISIBLE else View.GONE
        }
    }
}



