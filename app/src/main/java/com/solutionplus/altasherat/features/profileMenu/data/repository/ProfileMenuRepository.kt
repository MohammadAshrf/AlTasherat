package com.solutionplus.altasherat.features.profileMenu.data.repository

import com.solutionplus.altasherat.features.profileMenu.data.mapper.LogoutMapper
import com.solutionplus.altasherat.features.profileMenu.data.mapper.UserMapper
import com.solutionplus.altasherat.features.profileMenu.domain.model.Logout
import com.solutionplus.altasherat.features.profileMenu.domain.model.User
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository
import com.solutionplus.altasherat.features.profileMenu.domain.repository.local.IProfileMenuDS
import com.solutionplus.altasherat.features.profileMenu.domain.repository.remote.IProfileMenuRemoteDS

internal class ProfileMenuRepository(
    private val localDs: IProfileMenuDS,
    private val remoteDs: IProfileMenuRemoteDS
) : IProfileMenuRepository {
    override suspend fun getUser(): User =
        UserMapper.entityToDomain(localDs.getUser())

    override suspend fun getIsUserLoggedIn(): Boolean =
        localDs.isUserLoggedIn()

    override suspend fun logout(): Logout {
            return LogoutMapper.dtoToDomain(remoteDs.logout())
    }

    override suspend fun deleteUser() {
        localDs.deleteUser()
    }

    override suspend fun deleteAccessToken() {
        localDs.deleteAccessToken()
    }

    override suspend fun changeUserLoginState(isLoggedIn: Boolean) {
        localDs.changeUserLoginState(isLoggedIn)
    }


}