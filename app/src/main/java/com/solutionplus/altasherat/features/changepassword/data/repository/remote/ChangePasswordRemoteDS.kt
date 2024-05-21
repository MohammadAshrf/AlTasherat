package com.solutionplus.altasherat.features.changepassword.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.changepassword.data.model.dto.ChangePasswordDto
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.remote.IChangePasswordRemoteDS
import javax.inject.Inject

class ChangePasswordRemoteDS @Inject constructor(private val provider: INetworkProvider) :
    IChangePasswordRemoteDS {

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ChangePasswordDto? {
        return provider.post(
            responseWrappedModel = ChangePasswordDto::class.java,
            pathUrl = "change-password",
            headers = hashMapOf("accept" to "application/json",
                "Authorization" to "Bearer ${changePasswordRequest.token}"),
            requestBody = changePasswordRequest
        )
    }
}