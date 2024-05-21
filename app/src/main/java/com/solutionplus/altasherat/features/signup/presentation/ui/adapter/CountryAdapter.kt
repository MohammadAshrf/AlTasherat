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
import com.solutionplus.altasherat.feature.services.country.domain.models.Country

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

        val countryNameTextView: TextView = view.findViewById(R.id.countryNameTextView)
        val flagImageView: ImageView = view.findViewById(R.id.flagImageView)

        country?.let {
            countryNameTextView.text = it.name
            Glide.with(context)
                .load(it.flag)
                .into(flagImageView)
        }

        return view
    }
}