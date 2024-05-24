package com.solutionplus.altasherat.features.profileMenu.Presentation.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Constants.EMAIL_KEY_BUNDLE
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentProfileMenuBinding
import com.solutionplus.altasherat.features.login.presentation.ui.fragment.login.LoginContract
import com.solutionplus.altasherat.features.menu.Presentation.adapter.RowAdapter
import com.solutionplus.altasherat.features.profileMenu.Presentation.ProfileMenuViewModel
import com.solutionplus.altasherat.features.profileMenu.Presentation.adapter.OnRowItemClickListener
import com.solutionplus.altasherat.features.profileMenu.Presentation.adapter.RowItem
import com.solutionplus.altasherat.features.profileMenu.Presentation.adapter.createRowItem
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract
import com.solutionplus.altasherat.features.profileMenu.domain.model.User
import com.solutionplus.altasherat.presentation.ui.activity.main.AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileMenuFragment : BaseFragment<FragmentProfileMenuBinding>(), OnRowItemClickListener {

    private val viewModel: ProfileMenuViewModel by viewModels<ProfileMenuViewModel>()
    lateinit var emailBundle: Bundle


    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.GetUser)
        viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.IsUserLoggedIn)
    }

    override fun viewInit() {
        getAppVersion()
        binding.btnLogOut.setOnClickListener {
            viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.Logout)

        }
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) {
            when (it) {
                is ProfileMenuContract.ProfileMenuEvent.GetUser -> {
                    handleState(it.user)
                }

                is ProfileMenuContract.ProfileMenuEvent.IsUserLoggedIn -> {
                    setupRecyclerView(it.isUserLoggedIn)
                    logger.info(it.isUserLoggedIn.toString())

                    if (it.isUserLoggedIn) {
                        binding.viewProfileSection.fullView.visibility = View.VISIBLE
                        binding.btnLogOut.visibility = View.VISIBLE
                    } else {
                        binding.viewProfileSection.fullView.visibility = View.GONE
                    }
                }

                is ProfileMenuContract.ProfileMenuEvent.LogoutSuccess -> {
                    findNavController().navigate(R.id.action_profileMenuFragment_to_visaPlatformFragment)
                }
            }
        }

        collectFlowWithLifecycle(viewModel.viewState) { state ->
            if (state.isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    private fun handleState(user: User) {
        emailBundle = Bundle().apply {
            putString(EMAIL_KEY_BUNDLE, user.email)
        }
        if (user.id != -1) {
            if (!user.emailVerified) {
                showCustomSnackbar()
            }
            if (user.imageUrl != "") {
                Glide.with(this)
                    .load(user.imageUrl)
                    .into(binding.viewProfileSection.profilePictureMenu.profilePicture)

            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun getAppVersion() {
        val packageInfo =
            requireContext().packageManager!!.getPackageInfo(requireContext().packageName, 0)
        val version = packageInfo.versionName
        binding.tvVersion.text = "v $version"
    }


    private fun setupRecyclerView(isUserLoggedIn: Boolean) {
        val items = listOf(
            createRowItem(
                icon = if (isUserLoggedIn) R.drawable.ic_edt_password else R.drawable.ic_login,
                text = getString(R.string.login),
                destinationActivity = if (isUserLoggedIn) null else AuthenticationActivity::class.java,
                destinationFragmentId = if (isUserLoggedIn) R.id.action_profileMenuFragment_to_changePasswordFragment2 else null
            ),
            createRowItem(R.drawable.ic_info, getString(R.string.about_us), R.id.fakeFragment),
            createRowItem(R.drawable.ic_support, getString(R.string.terms), R.id.fakeFragment),
            createRowItem(
                R.drawable.ic_terms,
                getString(R.string.edit_password),
                R.id.fakeFragment
            ),
            createRowItem(R.drawable.ic_plicy, getString(R.string.policy), R.id.fakeFragment),
            createRowItem(R.drawable.ic_language, getString(R.string.language), R.id.fakeFragment)
        )

        val adapter = RowAdapter(items, this)

        binding.rvRow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRow.adapter = adapter
    }


    private fun showCustomSnackbar() {
        binding.messageVerefication.VerificationMessage.visibility = View.VISIBLE

        binding.messageVerefication.snackbarAction.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileMenuFragment_to_emailVerifiedFragment,
                emailBundle
            )
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

    companion object {
        val logger = getClassLogger()
    }
}