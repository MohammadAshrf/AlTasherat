package com.solutionplus.altasherat.features.profilemenu.Presentation.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentProfileMenuBinding
import com.solutionplus.altasherat.features.menu.Presentation.adapter.RowAdapter
import com.solutionplus.altasherat.features.menu.Presentation.adapter.RowItem


class ProfileMenuFragment : BaseFragment<FragmentProfileMenuBinding>() {
    override fun onFragmentReady(savedInstanceState: Bundle?) {
        checkUserLoggedIn()
    }

    private fun checkUserLoggedIn(): Boolean {
        //check if user is logged in
        return true
    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {
        setupRecyclerView()
        getAppVersion()
        updateUIBasedOnLoginStatus()
    }

    private fun getAppVersion() {
        val packageInfo =
            requireContext().packageManager!!.getPackageInfo(requireContext().packageName, 0)
        val version = packageInfo.versionName
        binding.tvVersion.text = "v $version"
    }

    private fun updateUIBasedOnLoginStatus() {
        if (!checkUserLoggedIn()) {
            binding.profilePictureMenu.profilePictureView.visibility = View.GONE
            binding.nameTextView.visibility = View.GONE
            binding.btnEditProfile.visibility = View.GONE
            binding.view.visibility = View.GONE
// Set the top margin of the RecyclerView
            val params = binding.rvRow.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = resources.getDimensionPixelSize(R.dimen._32sdp)
            binding.rvRow.layoutParams = params
        }
    }

    private fun setupRecyclerView() {
        val items = if (!checkUserLoggedIn()) {
            listOf(
                RowItem(R.drawable.ic_login, getString(R.string.login), R.id.fakeFragment),
                RowItem(R.drawable.ic_info, getString(R.string.about_us), R.id.fakeFragment),
                RowItem(R.drawable.ic_support, getString(R.string.terms), R.id.fakeFragment),
                RowItem(R.drawable.ic_terms, getString(R.string.edit_password), R.id.fakeFragment),
                RowItem(R.drawable.ic_plicy, getString(R.string.policy), R.id.fakeFragment),
                RowItem(R.drawable.ic_language, getString(R.string.language), R.id.fakeFragment),
            )
        } else {
            listOf(
                RowItem(
                    R.drawable.ic_edt_password,
                    getString(R.string.edit_password),
                    R.id.fakeFragment
                ),
                RowItem(R.drawable.ic_info, getString(R.string.about_us), R.id.fakeFragment),
                RowItem(R.drawable.ic_support, getString(R.string.terms), R.id.fakeFragment),
                RowItem(R.drawable.ic_terms, getString(R.string.edit_password), R.id.fakeFragment),
                RowItem(R.drawable.ic_plicy, getString(R.string.policy), R.id.fakeFragment),
                RowItem(R.drawable.ic_language, getString(R.string.language), R.id.fakeFragment),
            )
        }

        val navController = findNavController()
        val adapter = RowAdapter(items, navController)

        binding.rvRow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRow.adapter = adapter
    }
}