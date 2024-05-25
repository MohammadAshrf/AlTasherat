package com.solutionplus.altasherat.features.profileMenu.domain.repository.local

import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity

internal interface IProfileMenuDS {

    suspend fun getUser(): UserEntity

    suspend fun isUserLoggedIn(): Boolean

    suspend fun deleteUser()
    suspend fun changeUserLoginState(isLoggedIn: Boolean)
    suspend fun deleteAccessToken()
}