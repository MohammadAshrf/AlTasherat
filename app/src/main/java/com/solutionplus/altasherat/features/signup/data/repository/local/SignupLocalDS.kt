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
        val encryptUserData = Base64.getEncoder()
            .encodeToString(encryptionProvider.encryptData(bytes = bytesUser))
        logUserInfoAfterEncryption(userJson, encryptUserData)
        storageKV.saveEntry(StorageKeyEnum.USER, Gson().toJson(encryptUserData), String::class.java)
    }

    override suspend fun saveAccessToken(token: String) {
        val bytes = token.toByteArray()
        val encryptData = Base64.getEncoder()
            .encodeToString(encryptionProvider.encryptData(bytes = bytes))
        logAccessTokenAfterEncryption(token, encryptData)
        storageKV.saveEntry(StorageKeyEnum.ACCESS_TOKEN, encryptData, String::class.java)
    }

    override suspend fun getUser(): UserEntity {
        val userJson = storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java)
        val decryptedBytes =
            encryptionProvider.decryptData(Base64.getDecoder().decode(userJson))?.decodeToString()
        val result = Gson().fromJson(decryptedBytes, UserEntity::class.java)
        logUserInfoAfterDecryption(userJson!!,result)
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