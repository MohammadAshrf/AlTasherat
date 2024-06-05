package com.solutionplus.altasherat.features.personalInfo.domain.repository.local

import com.solutionplus.altasherat.features.personalInfo.data.models.entity.UserEntity

internal interface IUpdateProfileLocalDS {
    suspend fun savePersonalInfo(updateUserEntity: UserEntity)
    suspend fun getPersonalInfo() : UserEntity
    suspend fun hasUserInfo(): Boolean
}