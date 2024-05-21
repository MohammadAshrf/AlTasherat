package com.solutionplus.altasherat.features.signup.data.repository.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.solutionplus.altasherat.features.signup.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.signup.domain.repository.local.ISignupLocalDS
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.DataStoreKeyValueStorage
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
internal class SignupLocalDS (private val storageKV: IKeyValueStorageProvider, private val encryptionProvider: IEncryptionProvider):
    ISignupLocalDS {

    override suspend fun saveUser(user: UserEntity) {
        val userJson = Gson().toJson(user)
        val bytesUser = userJson.toByteArray()
        val encryptedUserData = encryptionProvider.encryptData(bytesUser)
        val encryptUserDataBase64 = Base64.getEncoder().encodeToString(encryptedUserData)
        logUserInfoAfterEncryption(userJson, encryptUserDataBase64)
        storageKV.saveEntry(StorageKeyEnum.USER, encryptUserDataBase64, String::class.java)
    }

    override suspend fun saveAccessToken(token: String) {
        val bytes = token.toByteArray()
        val encryptedData = encryptionProvider.encryptData(bytes)
        val encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedData)
        logAccessTokenAfterEncryption(token, encryptedDataBase64)
        storageKV.saveEntry(StorageKeyEnum.ACCESS_TOKEN, encryptedDataBase64, String::class.java)
    }

    override suspend fun getUser(): UserEntity {
        val userJsonBase64 = storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java)
        val encryptedBytes = Base64.getDecoder().decode(userJsonBase64)
        val decryptedBytes = encryptionProvider.decryptData(encryptedBytes)
        val decryptedString = decryptedBytes?.decodeToString()
        val result = Gson().fromJson(decryptedString, UserEntity::class.java)
        logUserInfoAfterDecryption(userJsonBase64!!, result)
        return result
    }

    private fun logUserInfoAfterEncryption( userJson: String,encryptUserData: String){
        logger.warning("the userInfo is --> $userJson ")
        logger.warning("the User Info after encryption --> $encryptUserData ")
    }

    private fun logAccessTokenAfterEncryption( token: String,encryptData: String){
        logger.warning("the Token is --> $token ")
        logger.warning("the token after encryption --> $encryptData ")
    }

    private fun logUserInfoAfterDecryption( userJson: String,result: UserEntity){
        logger.warning("userInfo encryption --> $userJson ")
        logger.warning("userInfo after decryption --> $result ")
    }

    companion object {
        private val logger = getClassLogger()
    }
}