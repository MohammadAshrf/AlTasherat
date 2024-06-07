package com.solutionplus.altasherat.features.profileMenu.data.repository.local

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.profileMenu.domain.repository.local.IProfileMenuDS
import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import java.util.Base64.getDecoder


internal class ProfileMenuDS(
    private val storageKV: IKeyValueStorageProvider,
    private val encryptionProvider: IEncryptionProvider
) :
    IProfileMenuDS {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUser(): UserEntity {
        val userJsonBase64 = storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java)
        val encryptedBytes = getDecoder().decode(userJsonBase64)
        val decryptedBytes = encryptionProvider.decryptData(encryptedBytes)
        val decryptedString = decryptedBytes?.decodeToString()
        val result = Gson().fromJson(decryptedString, UserEntity::class.java)
        logger.warning("userInfo encryption --> $userJsonBase64 ")
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