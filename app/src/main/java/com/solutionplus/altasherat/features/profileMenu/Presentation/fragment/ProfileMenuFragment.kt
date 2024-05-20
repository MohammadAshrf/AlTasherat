package com.solutionplus.altasherat.features.profileMenu.Presentation.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.view.marginTop
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.presentation.ui.base.frgment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentProfileMenuBinding
import com.solutionplus.altasherat.features.menu.Presentation.adapter.RowAdapter
import com.solutionplus.altasherat.features.menu.Presentation.adapter.RowItem
import com.solutionplus.altasherat.features.profileMenu.Presentation.ProfileMenuViewModel
import com.solutionplus.altasherat.features.profileMenu.ProfileMenuContract.ProfileMenuState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


@AndroidEntryPoint
class ProfileMenuFragment : BaseFragment<FragmentProfileMenuBinding>() {

    private val viewModel: ProfileMenuViewModel by viewModels<ProfileMenuViewModel>()


    override fun onFragmentReady(savedInstanceState: Bundle?) {

    }


    override fun subscribeToObservables() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                handleState(state)
                setupRecyclerView(state)
            }
        }
    }

    private fun handleState(state: ProfileMenuState) {
        isLoading(state.isLoading)

        if (state.isUserLoggedIn) {
            binding.nameTextView.text = state.fullName
            binding.profilePictureMenu.profilePictureView.visibility = View.VISIBLE
            binding.nameTextView.visibility = View.VISIBLE
            binding.btnEditProfile.visibility = View.VISIBLE
            binding.view.visibility = View.VISIBLE

        } else {
            binding.profilePictureMenu.profilePictureView.visibility = View.GONE
            binding.nameTextView.visibility = View.GONE
            binding.btnEditProfile.visibility = View.GONE
            binding.view.visibility = View.GONE
// Set the top margin of the RecyclerView
            val params = binding.rvRow.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = resources.getDimensionPixelSize(R.dimen._32sdp)
            binding.rvRow.layoutParams = params
        }

        if (state.doesProfilePictureExist && state.imageUrl != null) {
            binding.profilePictureMenu.profilePictureView.visibility = View.VISIBLE
            loadImageFromUrl(state.imageUrl, binding.profilePictureMenu.profilePicture)
        } else {
            // Hide profile picture
        }
    }

    override fun viewInit() {
        getAppVersion()
    }

    private fun getAppVersion() {
        val packageInfo =
            requireContext().packageManager!!.getPackageInfo(requireContext().packageName, 0)
        val version = packageInfo.versionName
        binding.tvVersion.text = "v $version"
    }

    private fun loadImageFromUrl(url: String?, imageView: ImageView) {
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

    private fun setupRecyclerView(state: ProfileMenuState) {
        val items = if (!state.isUserLoggedIn) {
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