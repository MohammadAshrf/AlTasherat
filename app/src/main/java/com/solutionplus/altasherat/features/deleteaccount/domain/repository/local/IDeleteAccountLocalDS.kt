package com.solutionplus.altasherat.features.deleteaccount.domain.repository.local

internal interface IDeleteAccountLocalDS {
    suspend fun deleteUser()
    suspend fun deleteAccessToken()
    suspend fun changeUserLoginState(isLoggedIn: Boolean)

}