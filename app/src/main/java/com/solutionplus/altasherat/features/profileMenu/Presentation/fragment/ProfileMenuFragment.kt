package com.solutionplus.altasherat.features.profileMenu.Presentation.fragment

import android.annotation.SuppressLint
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
import androidx.activity.viewModels
import androidx.core.view.marginTop
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentProfileMenuBinding
import com.solutionplus.altasherat.databinding.ViewCostomSnackbarBinding
import com.solutionplus.altasherat.features.menu.Presentation.adapter.RowAdapter
import com.solutionplus.altasherat.features.menu.Presentation.adapter.RowItem
import com.solutionplus.altasherat.features.profileMenu.Presentation.ProfileMenuViewModel
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract.ProfileMenuState
import com.solutionplus.altasherat.features.profileMenu.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


@AndroidEntryPoint
class ProfileMenuFragment : BaseFragment<FragmentProfileMenuBinding>() {

    private val viewModel: ProfileMenuViewModel by viewModels<ProfileMenuViewModel>()

    private var isUserLoggedIn = false

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        viewModel.onActionTrigger(ProfileMenuContract.ProfileMenuAction.CheckUserLogin)
    }


    override fun subscribeToObservables() {
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            if (state.isUserLoggedIn) {
                isUserLoggedIn = true
            }

        }

        collectFlowWithLifecycle(viewModel.singleEvent) {
            when (it) {
                is ProfileMenuContract.ProfileMenuEvent.NavigateToSignup -> {
                    findNavController().navigate(R.id.signupFragment)

                }

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
                showCustomSnackbar(binding.root)
            }

        }
        if (user.imageUrl != "") {
            loadImageFromUrl(user.imageUrl, binding.profilePictureMenu.profilePicture)
        } else {
            // Hide profile picture
        }
    }

    override fun viewInit() {
        getAppVersion()
        binding.backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getAppVersion() {
        val packageInfo =
            requireContext().packageManager!!.getPackageInfo(requireContext().packageName, 0)
        val version = packageInfo.versionName
        binding.tvVersion.text = "v $version"
    }

    private fun loadImageFromUrl(url: String?, imageView: ImageView) {
        //todo change it with glide
        if (url.isNullOrEmpty() || (!url.startsWith("http://") && !url.startsWith("https://"))) {
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val url = URL(url)
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(bmp)
                }
            } catch (e: Exception) {
                // Handle exception here
            }
        }
    }

    private fun setupRecyclerView() {
        val items = if (!isUserLoggedIn) {
            // Add margin to the top of the RecyclerView if the user is not logged in
            val params = binding.rvRow.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = resources.getDimensionPixelSize(R.dimen.margin_top_profile_menu)
            binding.rvRow.layoutParams = params
            listOf(
                RowItem(R.drawable.ic_login, getString(R.string.login), R.id.homeActivity),
                RowItem(R.drawable.ic_info, getString(R.string.about_us), R.id.fakeFragment),
                RowItem(R.drawable.ic_support, getString(R.string.terms), R.id.fakeFragment),
                RowItem(R.drawable.ic_terms, getString(R.string.edit_password), R.id.fakeFragment),
                RowItem(R.drawable.ic_plicy, getString(R.string.policy), R.id.fakeFragment),
                RowItem(R.drawable.ic_language, getString(R.string.language), R.id.fakeFragment),
            )
        } else {
            // Remove the top margin if the user is logged in
            val params = binding.rvRow.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = 0
            binding.rvRow.layoutParams = params

            listOf(
                RowItem(
                    R.drawable.ic_edt_password,
                    getString(R.string.edit_password),
                    R.id.changePasswordFragment
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

    fun showCustomSnackbar(view: View) {

        val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)

        val inflater = LayoutInflater.from(view.context)
        val customSnackbarBinding = ViewCostomSnackbarBinding.inflate(inflater)

        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

        val params = snackbarLayout.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        params.topMargin =
            resources.getDimensionPixelSize(R.dimen.your_margin_top) // Replace 'your_margin_top' with your actual top margin value

        snackbarLayout.layoutParams = params

        snackbarLayout.background = ColorDrawable(Color.TRANSPARENT)

        snackbarLayout.addView(customSnackbarBinding.root, 0)

        // Set the text and click listeners for the views in your custom layout
        //customSnackbarBinding.snackbarText.text = "Your custom text"
        customSnackbarBinding.snackbarAction.setOnClickListener {
            findNavController().navigate(R.id.emailVerifiedFragment)
            snackbar.dismiss()
        }
        customSnackbarBinding.snackbarClose.setOnClickListener {
            // Dismiss the Snackbar when the close button is clicked
            snackbar.dismiss()
        }

        // Show the Snackbar
        snackbar.show()
    }
}