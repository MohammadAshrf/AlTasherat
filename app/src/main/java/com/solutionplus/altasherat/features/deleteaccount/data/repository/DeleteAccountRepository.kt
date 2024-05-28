package com.solutionplus.altasherat.features.deleteaccount.data.repository

import com.solutionplus.altasherat.features.deleteaccount.data.mapper.DeleteAccountMapper
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.IDeleteAccountRepository
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.local.IDeleteAccountLocalDS
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.remote.IdeleteAccountRemoteDS
import com.solutionplus.altasherat.features.profileMenu.domain.repository.local.IProfileMenuDS
import com.solutionplus.altasherat.features.profileMenu.domain.repository.remote.IProfileMenuRemoteDS

internal class DeleteAccountRepository(
    private val localDs: IDeleteAccountLocalDS,
    private val remoteDs: IdeleteAccountRemoteDS
):IDeleteAccountRepository {
    override suspend fun deleteAccount(deleteAccountRequest: DeleteAccountRequest): DeleteAccountRequest {
      return DeleteAccountMapper.dtoToDomain(remoteDs.deleteAccount(deleteAccountRequest)!!)
    }

    override suspend fun deleteUser() {
        localDs.deleteUser()
    }

    override suspend fun deleteAccessToken() {
        localDs.deleteAccessToken()
    }
}