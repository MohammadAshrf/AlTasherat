package com.solutionplus.altasherat.features.personalInfo.data.repository.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.personalInfo.data.models.entity.UpdateUserEntity
import com.solutionplus.altasherat.features.personalInfo.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.personalInfo.domain.repository.local.IUpdateUserLocalDS
import com.solutionplus.altasherat.features.services.country.data.repository.local.CountriesLocalDS
import java.util.Base64

internal class UpdateUserLocalDS(
    private val storageKV: IKeyValueStorageProvider,
    private val encryptionProvider: IEncryptionProvider
) : IUpdateUserLocalDS {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun savePersonalInfo(updateUserEntity: UpdateUserEntity) {
        val updatedUserJson = Gson().toJson(updateUserEntity)
        val bytesUser = updatedUserJson.toByteArray()
        val encryptedUserData = encryptionProvider.encryptData(bytesUser)
        val encryptUserDataBase64 = Base64.getEncoder().encodeToString(encryptedUserData)
        logSavedUserInfoAfterEncryption(updatedUserJson, encryptUserDataBase64)
        storageKV.saveEntry(StorageKeyEnum.USER, encryptUserDataBase64, String::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getPersonalInfo(): UserEntity {
        val userJsonBase64 = storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java)
        val encryptedBytes = Base64.getDecoder().decode(userJsonBase64)
        val decryptedBytes = encryptionProvider.decryptData(encryptedBytes)
        val decryptedString = decryptedBytes?.decodeToString()
        val result = Gson().fromJson(decryptedString, UserEntity::class.java)
        logUserInfoAfterDecryption(userJsonBase64, result)
        return result
    }

    override suspend fun hasUserInfo(): Boolean {
        val key = storageKV.hasKey(StorageKeyEnum.USER, false)
        logger.info(key.toString())
        return key
    }

    private fun logSavedUserInfoAfterEncryption(userJson: String, encryptUserData: String) {
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