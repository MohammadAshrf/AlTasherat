package com.solutionplus.altasherat.features.profileMenu.Presentation.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentProfileMenuBinding
import com.solutionplus.altasherat.databinding.ViewCostomSnackbarBinding
import com.solutionplus.altasherat.features.menu.Presentation.adapter.RowAdapter
import com.solutionplus.altasherat.features.profileMenu.Presentation.ProfileMenuViewModel
import com.solutionplus.altasherat.features.profileMenu.Presentation.adapter.OnRowItemClickListener
import com.solutionplus.altasherat.features.profileMenu.Presentation.adapter.RowItem
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract
import com.solutionplus.altasherat.features.profileMenu.domain.model.User
import com.solutionplus.altasherat.presentation.ui.activity.main.AuthenticationActivity
import com.solutionplus.altasherat.presentation.ui.activity.main.HomeActivity
import com.solutionplus.altasherat.presentation.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


@AndroidEntryPoint
class ProfileMenuFragment : BaseFragment<FragmentProfileMenuBinding>(), OnRowItemClickListener {

    private val viewModel: ProfileMenuViewModel by viewModels<ProfileMenuViewModel>()

    private var isUserLoggedIn = false

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.CheckUserLogin)
    }
    override fun viewInit() {
        getAppVersion()
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            if (state.isUserLoggedIn) {
                isUserLoggedIn = true
            }
        }

        collectFlowWithLifecycle(viewModel.singleEvent) {
            when (it) {
                is ProfileMenuContract.ProfileMenuEvent.IsUserLoggedIn -> {
                    setupRecyclerView()
                    handleState(it.user)
                }
            }
        }
    }

    private fun handleState(user: User) {

        if (user.id != -1) {
            binding.nameTextView.text = user.fullName
            binding.profilePictureMenu.profilePictureView.visibility = View.VISIBLE
            binding.nameTextView.visibility = View.VISIBLE
            binding.btnEditProfile.visibility = View.VISIBLE
            binding.view.visibility = View.VISIBLE
            binding.btnLogOut.visibility = View.VISIBLE

            if (!user.emailVerified) {
                showCustomSnackbar()
            }

        }
        if (user.imageUrl != "") {
            Glide.with(this)
                .load(user.imageUrl)
                .into(binding.profilePictureMenu.profilePicture)
        }
    }



    @SuppressLint("SetTextI18n")
    private fun getAppVersion() {
        val packageInfo =
            requireContext().packageManager!!.getPackageInfo(requireContext().packageName, 0)
        val version = packageInfo.versionName
        binding.tvVersion.text = "v $version"
    }


    private fun setupRecyclerView() {
        val items = if (!isUserLoggedIn) {
            listOf(
                RowItem(
                    R.drawable.ic_login,
                    getString(R.string.login),
                    destinationActivity = AuthenticationActivity::class.java
                ),
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
                    R.id.action_profileMenuFragment_to_changePasswordFragment2
                ),
                RowItem(R.drawable.ic_info, getString(R.string.about_us), R.id.fakeFragment),
                RowItem(R.drawable.ic_support, getString(R.string.terms), R.id.fakeFragment),
                RowItem(R.drawable.ic_terms, getString(R.string.edit_password), R.id.fakeFragment),
                RowItem(R.drawable.ic_plicy, getString(R.string.policy), R.id.fakeFragment),
                RowItem(R.drawable.ic_language, getString(R.string.language), R.id.fakeFragment),
            )
        }

        val adapter = RowAdapter(items, this)

        binding.rvRow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRow.adapter = adapter
    }

    fun showCustomSnackbar() {
        binding.messageVerefication.VerificationMessage.visibility = View.VISIBLE

        binding.messageVerefication.snackbarAction.setOnClickListener {
            findNavController().navigate(R.id.emailVerifiedFragment)
            binding.messageVerefication.VerificationMessage.visibility = View.GONE
        }
        binding.messageVerefication.snackbarClose.setOnClickListener {
            binding.messageVerefication.VerificationMessage.visibility = View.GONE
        }
    }

    override fun onRowItemClick(destinationFragmentId: Int?, destinationActivity: Class<*>?) {
        destinationFragmentId?.let {
            findNavController().navigate(it)
        }
        destinationActivity?.let {
            val intent = Intent(context, it)
            startActivity(intent)
        }
    }

}