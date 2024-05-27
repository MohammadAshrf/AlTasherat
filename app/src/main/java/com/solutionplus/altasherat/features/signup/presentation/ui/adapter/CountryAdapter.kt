package com.solutionplus.altasherat.features.signup.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.features.services.country.domain.models.Country

class CountryAdapter(context: Context, countries: List<Country>) :
    ArrayAdapter<Country>(context, 0, countries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val country = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_country_spinner,
            parent,
            false
        )

        val countryCodeTextView: TextView = view.findViewById(R.id.countryCode)
        val flagImageView: TextView = view.findViewById(R.id.flagImageView)

        country?.let {
            countryCodeTextView.text = it.phoneCode
            flagImageView.text = it.flag
            // Set visibility to VISIBLE for demonstration purposes
            countryCodeTextView.visibility = View.VISIBLE
        }
        return view
    }
}