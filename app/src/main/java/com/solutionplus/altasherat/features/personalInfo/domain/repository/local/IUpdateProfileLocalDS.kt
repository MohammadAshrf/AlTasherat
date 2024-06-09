package com.solutionplus.altasherat.features.personalInfo.domain.repository.local

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity

internal interface IUpdateProfileLocalDS {
    suspend fun hasUserInfo(): Boolean
}