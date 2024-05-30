package com.solutionplus.altasherat.features.deleteaccount.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.local.IDeleteAccountLocalDS

internal class DeleteAccountLocalDS (
    private val storageKV: IKeyValueStorageProvider
):IDeleteAccountLocalDS {
    override suspend fun deleteUser() {
        storageKV.deleteEntry<String>(StorageKeyEnum.USER, String::class.java)
    }

    override suspend fun deleteAccessToken() {
        storageKV.deleteEntry<String>(StorageKeyEnum.ACCESS_TOKEN, String::class.java)
    }

    override suspend fun changeUserLoginState(isLoggedIn: Boolean) {
        storageKV.updateEntry(StorageKeyEnum.IS_USER_LOGGED_IN, isLoggedIn, Boolean::class.java)
    }
}