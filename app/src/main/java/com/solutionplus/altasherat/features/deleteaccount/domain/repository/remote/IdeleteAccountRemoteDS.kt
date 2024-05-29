package com.solutionplus.altasherat.features.deleteaccount.domain.repository.remote

import com.solutionplus.altasherat.features.deleteaccount.data.model.dto.DeleteAccountDto
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest

internal interface IdeleteAccountRemoteDS {

    suspend fun deleteAccount(deleteAccountRequest: DeleteAccountRequest): DeleteAccountDto?
}