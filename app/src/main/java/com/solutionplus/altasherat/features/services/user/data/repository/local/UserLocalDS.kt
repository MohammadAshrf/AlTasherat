package com.solutionplus.altasherat.features.services.user.data.repository.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.services.user.domain.repository.local.IUserLocalDS
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
internal class UserLocalDS(
    private val storageKV: IKeyValueStorageProvider,
    private val encryptionProvider: IEncryptionProvider
) : IUserLocalDS {
    override suspend fun saveUser(user: UserEntity) {
        val userJson = Gson().toJson(user)
        val bytesUser = userJson.toByteArray()
        val encryptedUserData = encryptionProvider.encryptData(bytesUser)
        val encryptUserDataBase64 = Base64.getEncoder().encodeToString(encryptedUserData)
        logUserInfoAfterEncryption(userJson, encryptUserDataBase64)
        storageKV.saveEntry(StorageKeyEnum.USER, encryptUserDataBase64, String::class.java)
        storageKV.saveEntry(StorageKeyEnum.IS_USER_LOGGED_IN, true, Boolean::class.java)
    }

    override suspend fun getUserLocal(): UserEntity {
        val userJsonBase64 = storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java)
        val encryptedBytes = Base64.getDecoder().decode(userJsonBase64)
        val decryptedBytes = encryptionProvider.decryptData(encryptedBytes)
        val decryptedString = decryptedBytes?.decodeToString()
        val result = Gson().fromJson(decryptedString, UserEntity::class.java)
        logUserInfoAfterDecryption(userJsonBase64!!, result)
        return result
    }

    private fun logUserInfoAfterEncryption(userJson: String, encryptUserData: String) {
        logger.warning("the userInfo is --> $userJson ")
        logger.warning("the User Info after encryption --> $encryptUserData ")
    }

    private fun logUserInfoAfterDecryption(userJson: String, result: UserEntity) {
        logger.warning("userInfo encryption --> $userJson ")
        logger.warning("userInfo after decryption --> $result ")
    }

    companion object {
        private val logger = getClassLogger()
    }
}