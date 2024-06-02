package com.solutionplus.altasherat.features.personalInfo.domain.repository.local

import com.solutionplus.altasherat.features.personalInfo.data.models.entity.UpdateUserEntity
import com.solutionplus.altasherat.features.personalInfo.data.models.entity.UserEntity

internal interface IUpdateUserLocalDS {
    suspend fun savePersonalInfo(updateUserEntity: UpdateUserEntity)
    suspend fun getPersonalInfo() : UserEntity
    suspend fun hasUserInfo(): Boolean
}