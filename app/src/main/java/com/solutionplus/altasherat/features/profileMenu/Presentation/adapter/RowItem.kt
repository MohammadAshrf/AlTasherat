package com.solutionplus.altasherat.features.profileMenu.Presentation.adapter

data class RowItem(
    val icon: Int,
    val text: String,
    val destinationFragmentId: Int? = null,
    val destinationActivity: Class<*>? = null
)

fun createRowItem(
    icon: Int,
    text: String,
    destinationFragmentId: Int? = null,
    destinationActivity: Class<*>? = null
): RowItem {
    return RowItem(icon, text, destinationFragmentId, destinationActivity)
}