package com.solutionplus.altasherat.features.profileMenu.domain.repository

import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity

interface IProfileMenuRepository {
    suspend fun getUser(): UserEntity?
}