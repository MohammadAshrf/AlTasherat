package com.solutionplus.altasherat.features.onboarding.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.FragmentOnBoardingFirstBinding

class OnBoardingAdapter(private val pages: List<OnBoardingItem>) : RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: FragmentOnBoardingFirstBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(page: OnBoardingItem) {
            binding.image.setImageResource(page.image)
            binding.cardView.onboardingDescription.text = page.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentOnBoardingFirstBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val page = pages[position]
        holder.bind(page)
    }

    override fun getItemCount(): Int = pages.size
}