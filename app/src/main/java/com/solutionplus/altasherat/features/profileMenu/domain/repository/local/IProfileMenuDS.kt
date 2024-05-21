package com.solutionplus.altasherat.features.profileMenu.domain.repository.local

import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity

internal interface IProfileMenuDS {

    suspend fun getUser(): UserEntity
}