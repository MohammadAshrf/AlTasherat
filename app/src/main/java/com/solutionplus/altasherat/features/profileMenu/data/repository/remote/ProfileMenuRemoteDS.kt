package com.solutionplus.altasherat.features.profileMenu.data.repository.remote

import com.solutionplus.altasherat.common.data.repository.remote.interceptors.authInterceptor.AuthTokenProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.login.data.model.dto.LoginDto
import com.solutionplus.altasherat.features.profileMenu.data.model.dto.LogoutDto
import com.solutionplus.altasherat.features.profileMenu.domain.model.Logout
import com.solutionplus.altasherat.features.profileMenu.domain.repository.remote.IProfileMenuRemoteDS
import kotlinx.coroutines.runBlocking

class ProfileMenuRemoteDS(private val provider: INetworkProvider
) : IProfileMenuRemoteDS {
    override suspend fun logout(): LogoutDto{
        return provider.delete(
            responseWrappedModel = LogoutDto::class.java,
            pathUrl = "logout",
        )
    }

}