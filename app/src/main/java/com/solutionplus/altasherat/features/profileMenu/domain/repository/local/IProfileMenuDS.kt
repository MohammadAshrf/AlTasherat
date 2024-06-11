package com.solutionplus.altasherat.features.profileMenu.domain.repository.local

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity

internal interface IProfileMenuDS {

    suspend fun getUser(): UserEntity
    suspend fun isUserLoggedIn(): Boolean
    suspend fun deleteUser()
    suspend fun changeUserLoginState(isLoggedIn: Boolean)
    suspend fun deleteAccessToken()
}