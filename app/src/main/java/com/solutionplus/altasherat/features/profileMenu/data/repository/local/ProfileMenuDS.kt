package com.solutionplus.altasherat.features.profileMenu.data.repository.local

import android.util.Base64
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.repository.local.IProfileMenuDS


internal class ProfileMenuDS(
    private val storageKV: IKeyValueStorageProvider,
    private val encryptionProvider: IEncryptionProvider
) :
    IProfileMenuDS {

    override suspend fun getUser(): UserEntity {
            val userJson = storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java)
            logger.info("userInfo before decryption --> $userJson ")
            val decodedBytes: ByteArray = Base64.decode(userJson, Base64.DEFAULT)
            val decryptedBytes =
                decodedBytes.let { encryptionProvider.decryptData(it)?.decodeToString() }

            val result = decryptedBytes.let { Gson().fromJson(it, UserEntity::class.java) }
            logger.warning("userInfo after decryption --> $result ")

            return result
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return storageKV.getEntry(StorageKeyEnum.IS_USER_LOGGED_IN, false, Boolean::class.java)
    }

    override suspend fun deleteUser() {
        storageKV.deleteEntry<String>(StorageKeyEnum.USER, String::class.java)
    }

    override suspend fun deleteAccessToken() {
        storageKV.deleteEntry<String>(StorageKeyEnum.ACCESS_TOKEN, String::class.java)
    }

    override suspend fun changeUserLoginState(isLoggedIn: Boolean) {
        storageKV.updateEntry(StorageKeyEnum.IS_USER_LOGGED_IN, isLoggedIn, Boolean::class.java)
    }

    companion object {
        val logger = getClassLogger()
    }
}