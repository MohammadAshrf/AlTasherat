package com.solutionplus.altasherat.features.personalInfo.domain.repository.local

import com.solutionplus.altasherat.features.personalInfo.data.models.entity.UpdateUserEntity

internal interface IUpdateUserLocalDS {
    suspend fun savePersonalInfo(updateUserEntity: UpdateUserEntity)
    suspend fun getPersonalInfo() : UpdateUserEntity
}