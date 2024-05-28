package com.solutionplus.altasherat.features.profileMenu.domain.repository.remote

import com.solutionplus.altasherat.features.profileMenu.data.model.dto.LogoutDto
import com.solutionplus.altasherat.features.profileMenu.domain.model.Logout

interface IProfileMenuRemoteDS {
    suspend fun logout(): LogoutDto
}