package com.solutionplus.altasherat.features.profileMenu.presentation.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Constants.EMAIL_KEY_BUNDLE
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentProfileMenuBinding
import com.solutionplus.altasherat.features.menu.Presentation.adapter.RowAdapter
import com.solutionplus.altasherat.features.profileMenu.presentation.ProfileMenuViewModel
import com.solutionplus.altasherat.features.profileMenu.presentation.adapter.OnRowItemClickListener
import com.solutionplus.altasherat.features.profileMenu.presentation.adapter.RowItem
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.presentation.ui.activity.main.AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileMenuFragment : BaseFragment<FragmentProfileMenuBinding>(), OnRowItemClickListener {

    private val viewModel: ProfileMenuViewModel by viewModels<ProfileMenuViewModel>()
    private lateinit var emailBundle: Bundle

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.IsUserLoggedIn)
    }

    override fun viewInit() {
        getAppVersion()
        binding.btnLogOut.setOnClickListener {
            viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.Logout)
        }

        binding.viewProfileSection.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileMenuFragment_to_personalInfoFragment)
        }
    }

    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.singleEvent) {
            when (it) {
                is ProfileMenuContract.ProfileMenuEvent.GetUser -> {
                    handleUserState(it.user)
                }

                is ProfileMenuContract.ProfileMenuEvent.IsUserLoggedIn -> {
                    setupRecyclerView(it.isUserLoggedIn)
                    logger.info(it.isUserLoggedIn.toString())

                    if (it.isUserLoggedIn) {
                        binding.viewProfileSection.fullView.visibility = View.VISIBLE
                        binding.btnLogOut.visibility = View.VISIBLE
                        viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.GetUser)
                    } else {
                        binding.viewProfileSection.fullView.visibility = View.GONE
                    }
                }

                is ProfileMenuContract.ProfileMenuEvent.LogoutSuccess -> {
                    showSnackBar(it.message, false)
                    viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.IsUserLoggedIn)
                    findNavController().navigate(R.id.action_profileMenuFragment_to_visaPlatformFragment)
                }

                else -> {}
            }
        }

        collectFlowWithLifecycle(viewModel.viewState) { it ->
            it.exception?.let {
                handleHttpExceptions(it)
            }
            if (it.isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }


    private fun handleUserState(user: User) {
        emailBundle = Bundle().apply {
            putString(EMAIL_KEY_BUNDLE, user.email)
        }
        if (!user.emailVerified) {
            showCustomSnackBar()
        }
        if (user.image.path != "") {
            Glide.with(this)
                .load(user.image.path)
                .into(binding.viewProfileSection.profilePictureMenu.profilePicture)

        }
        binding.viewProfileSection.nameTextView.text = user.firstname + " " + user.lastname
    }

    @SuppressLint("SetTextI18n")
    private fun getAppVersion() {
        val packageInfo =
            requireContext().packageManager!!.getPackageInfo(requireContext().packageName, 0)
        val version = packageInfo.versionName
        binding.tvVersion.text = "v$version"
    }


    private fun setupRecyclerView(isUserLoggedIn: Boolean) {
        val items = listOf(
            RowItem(
                icon = if (isUserLoggedIn) R.drawable.ic_edt_password else R.drawable.ic_login,
                text = if (isUserLoggedIn) getString(R.string.edit_password) else getString(R.string.login),
                destinationActivity = if (isUserLoggedIn) null else AuthenticationActivity::class.java,
                destinationFragmentId = if (isUserLoggedIn) R.id.action_profileMenuFragment_to_changePasswordFragment2 else null
            ),

            RowItem(
                R.drawable.ic_info,
                getString(R.string.about_us),
                R.id.action_profileMenuFragment_to_aboutFragment
            ),
            RowItem(R.drawable.ic_support, getString(R.string.contact_us), R.id.contactUsFragment),
            RowItem(
                R.drawable.ic_terms,
                getString(R.string.terms_and_conditions),
                R.id.action_profileMenuFragment_to_termsFragment
            ),
            RowItem(
                R.drawable.ic_policy,
                getString(R.string.privacy_policy),
                R.id.action_profileMenuFragment_to_privacyFragment
            ),
            RowItem(R.drawable.ic_language, getString(R.string.language), R.id.changeLanguage)
        )

        val adapter = RowAdapter(items, this)

        binding.rvRow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRow.adapter = adapter
    }


    private fun showCustomSnackBar() {
        binding.messageVerification.VerificationMessage.visibility = View.VISIBLE

        binding.messageVerification.snackbarAction.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileMenuFragment_to_emailVerifiedFragment,
                emailBundle
            )
            binding.messageVerification.VerificationMessage.visibility = View.GONE
        }
        binding.messageVerification.snackbarClose.setOnClickListener {
            binding.messageVerification.VerificationMessage.visibility = View.GONE
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
