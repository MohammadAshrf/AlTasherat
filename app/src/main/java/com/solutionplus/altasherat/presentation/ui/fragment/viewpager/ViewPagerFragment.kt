package com.solutionplus.altasherat.presentation.ui.fragment.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.databinding.FragmentViewPagerBinding
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginFragment
import com.solutionplus.altasherat.features.signup.presentation.ui.SignupFragment
import com.solutionplus.altasherat.presentation.ui.fragment.viewpager.adapter.ViewPagerAdapter

class ViewPagerFragment : Fragment() {

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var binding: FragmentViewPagerBinding

    private val signupFragment = SignupFragment()
    private val loginFragment = LoginFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize tabs with custom views
        setupTabLayout()

        val fragmentManager: FragmentManager = childFragmentManager
        adapter = ViewPagerAdapter(fragmentManager, lifecycle, loginFragment, signupFragment)
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
                updateButtonText(position)
                updateImageViewVisibility(position) // Update visibility of ImageView elements
                updateTabUnderline(position) // Update underline visibility
            }
        })

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.setCurrentItem(tab.position, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.btnLoginAndSignup.setOnClickListener {
            when (binding.viewPager.currentItem) {
                0 -> loginFragment.onLoginAction()
                1 -> signupFragment.onSignupAction()
                else -> throw LeonException.Unknown("Unknown tab position: ${binding.viewPager.currentItem}")
            }
        }

        // Initial button text, image visibility, and underline setup
        updateButtonText(binding.viewPager.currentItem)
        updateImageViewVisibility(binding.viewPager.currentItem)
        updateTabUnderline(binding.viewPager.currentItem)
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
                binding.imageView5.visibility = View.GONE
                binding.imageViewp2.visibility = View.VISIBLE
            }
            1 -> {
                binding.imageView5.visibility = View.VISIBLE
                binding.imageViewp2.visibility = View.GONE
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



