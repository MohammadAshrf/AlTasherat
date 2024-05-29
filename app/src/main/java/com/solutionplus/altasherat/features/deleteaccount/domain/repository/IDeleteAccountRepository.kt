package com.solutionplus.altasherat.features.deleteaccount.domain.repository

import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest

interface IDeleteAccountRepository {
    suspend fun deleteAccount(deleteAccountRequest: DeleteAccountRequest): DeleteAccountRequest
    suspend fun deleteUser()
    suspend fun deleteAccessToken()
}