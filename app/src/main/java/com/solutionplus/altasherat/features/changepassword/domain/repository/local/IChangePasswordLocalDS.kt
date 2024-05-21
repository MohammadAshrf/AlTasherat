package com.solutionplus.altasherat.features.changepassword.domain.repository.local

internal interface IChangePasswordLocalDS {
    suspend fun getAccessToken(): String
}