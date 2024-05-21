package com.solutionplus.altasherat.features.profileMenu.domain.repository

import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.model.User

interface IProfileMenuRepository {
    suspend fun getUser(): User
}