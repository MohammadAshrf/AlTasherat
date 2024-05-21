package com.solutionplus.altasherat.features.changepassword.data.model.entity

data class ChangePasswordEntity (
    val oldPassword: String,
    val newPassword: String,
    val newPasswordConfirmation: String,
    val token: String
)