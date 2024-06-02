package com.solutionplus.altasherat.features.profileMenu.data.model.entity

internal data class ImageEntity(
    val id: Int,
    val type: String,
    val path: String,
    val title: String,
    val updatedAt: String,
    val description: String,
    val createdAt: String,
    val main: Boolean,
    val priority: Int
)