package com.solutionplus.altasherat.features.deleteaccount.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.changepassword.data.model.dto.ChangePasswordDto
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.deleteaccount.data.model.dto.DeleteAccountDto
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.remote.IdeleteAccountRemoteDS
import javax.inject.Inject

class DeleteAccountRemoteDS @Inject constructor(private val provider: INetworkProvider) : IdeleteAccountRemoteDS{

    override suspend fun deleteAccount(deleteAccountRequest: DeleteAccountRequest): DeleteAccountDto? {
        return provider.post(
            responseWrappedModel = DeleteAccountDto::class.java,
            pathUrl = "delete-account",
            requestBody = deleteAccountRequest
        )
    }
}