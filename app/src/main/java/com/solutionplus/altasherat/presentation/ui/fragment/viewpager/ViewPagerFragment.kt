package com.solutionplus.altasherat.presentation.ui.fragment.viewpager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentViewPagerBinding
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginFragment
import com.solutionplus.altasherat.features.signup.presentation.ui.SignupFragment
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
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

    private fun actions() {
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
    private fun setupListener() {
        binding.btnLoginAndSignup.setOnClickListener {
            when (binding.viewPager.currentItem) {
                0 -> loginFragment.onLoginAction()
                1 -> signupFragment.onSignupAction()
                else -> throw LeonException.Unknown("Unknown tab position: ${binding.viewPager.currentItem}")
            }
        }
        binding.skip.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerview() {
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
                updateCardViewSize(position)
                updateTabTextColor(position)
            }
        })
    }

    private fun updateCardViewSize(position: Int) {
        val params = binding.cardView.layoutParams
        when (position) {
            0 -> {
                params.height = resources.getDimensionPixelSize(R.dimen.height_for_position_0)
                binding.cardView.layoutParams = params
            }

            1 -> {
                params.height = resources.getDimensionPixelSize(R.dimen.height_for_position_1)
                binding.cardView.layoutParams = params
            }
        }
    }

    private fun setupTabLayoutSelectedListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.setCurrentItem(tab.position, true)
                updateTabTextColor(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun setupTabLayout() {
        val inflater = LayoutInflater.from(requireContext())
        val tabTitles = mutableListOf(getString(R.string.sigin_in), getString(R.string.new_account))
        tabTitles.forEachIndexed { _, title ->
            val tab = binding.tabLayout.newTab()
            val tabView = inflater.inflate(R.layout.custom_tab_with_underline, null)
            tab.customView = tabView
            tabView.findViewById<TextView>(R.id.tab_text).text = title
            binding.tabLayout.addTab(tab)
        }
    }

    private fun updateButtonText(position: Int) {
        when (position) {
            0 -> binding.btnLoginAndSignup.text = getString(R.string.sigin_in)
            1 -> binding.btnLoginAndSignup.text = getString(R.string.new_account)
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

    private fun updateTabTextColor(selectedPosition: Int) {
        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = binding.tabLayout.getTabAt(i)
            val tabTextView = tab?.customView?.findViewById<TextView>(R.id.tab_text)
            if (i == selectedPosition) {
                tabTextView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            } else {
                tabTextView?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.textInputIcon
                    )
                )
            }
        }
    }
}