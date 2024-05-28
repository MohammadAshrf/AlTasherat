package com.solutionplus.altasherat.features.profileMenu.domain.repository

import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.model.Logout
import com.solutionplus.altasherat.features.profileMenu.domain.model.User

interface IProfileMenuRepository {
    suspend fun getUser(): User
    suspend fun getIsUserLoggedIn(): Boolean
    suspend fun logout(): Logout
    suspend fun deleteUser()
    suspend fun deleteAccessToken()
    suspend fun changeUserLoginState(isLoggedIn: Boolean)
}