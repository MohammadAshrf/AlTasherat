package com.solutionplus.altasherat.features.profileMenu.presentation.adapter

data class RowItem(
    val icon: Int,
    val text: String,
    val destinationFragmentId: Int? = null,
    val destinationActivity: Class<*>? = null
)
