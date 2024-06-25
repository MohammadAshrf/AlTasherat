package com.solutionplus.altasherat.features.language.presentation

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.solutionplus.altasherat.presentation.adapters.singleSelection.SingleSelection
import kotlinx.parcelize.Parcelize

@Parcelize
data class Language(
    override var id: Int,
    override var name: String,
    val currentLocale: String,
    override var selected: Boolean,
    @DrawableRes val icon: Int
) : SingleSelection, Parcelable{
    override fun getIconRes(): Int = icon
}