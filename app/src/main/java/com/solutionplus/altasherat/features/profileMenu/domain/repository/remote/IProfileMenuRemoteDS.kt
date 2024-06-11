package com.solutionplus.altasherat.features.profileMenu.domain.repository.remote

import com.solutionplus.altasherat.features.profileMenu.data.model.dto.LogoutDto

interface IProfileMenuRemoteDS {
    suspend fun logout(): LogoutDto
}