package com.solutionplus.altasherat.features.profileMenu.data.repository

import com.solutionplus.altasherat.features.profileMenu.data.mapper.UserMapper
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.model.User
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository
import com.solutionplus.altasherat.features.profileMenu.domain.repository.local.IProfileMenuDS

internal class ProfileMenuRepository(
    private val localDs: IProfileMenuDS,
) : IProfileMenuRepository {
    override suspend fun getUser(): User =
        UserMapper.entityToDomain(localDs.getUser())
}