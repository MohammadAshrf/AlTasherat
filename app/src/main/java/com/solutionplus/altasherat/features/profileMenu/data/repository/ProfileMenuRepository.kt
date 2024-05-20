package com.solutionplus.altasherat.features.profileMenu.data.repository

import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository
import com.solutionplus.altasherat.features.profileMenu.domain.repository.local.IProfileMenuDS

internal class ProfileMenuRepository(
    private val localDs: IProfileMenuDS,
) : IProfileMenuRepository {
    override suspend fun getUser(): UserEntity? =
        localDs.getUser()
}