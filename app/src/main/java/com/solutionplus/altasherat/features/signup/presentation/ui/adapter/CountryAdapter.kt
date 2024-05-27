package com.solutionplus.altasherat.features.signup.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.ItemCountrySpinnerBinding
import com.solutionplus.altasherat.databinding.ItemCountrySpinnerWithPhoneBinding
import com.solutionplus.altasherat.features.services.country.domain.models.Country

internal class CountryAdapter
    (private val context: Context, private val countries: List<Country>) : BaseAdapter() {
    override fun getCount(): Int = countries.size

    override fun getItem(position: Int): Country = countries[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemCountrySpinnerWithPhoneBinding
        val view: View
        if (convertView == null) {
            binding = ItemCountrySpinnerWithPhoneBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemCountrySpinnerWithPhoneBinding
            view = convertView
        }
        val country = countries[position]
        binding.flagImageView.text = country.flag
        binding.countryCode.text = "(${country.phoneCode})"
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

}