package com.solutionplus.altasherat.features.services.country.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.ItemCountrySpinnerWithPhoneBinding
import com.solutionplus.altasherat.features.services.country.domain.models.Country

internal class CountryCodeSpinnerAdapter
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
        val countryCodeWithoutZeroZero = country.phoneCode.replaceFirst("^00".toRegex(), "")
        binding.countryCode.text = context.getString(R.string.binding_phone_code, countryCodeWithoutZeroZero)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

    fun getPosition(country: Country): Int {
        return countries.indexOfFirst { it.id == country.id }
    }

}