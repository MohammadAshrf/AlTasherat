package com.solutionplus.altasherat.features.changepassword.data.repository.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.changepassword.domain.repository.local.IChangePasswordLocalDS
import com.solutionplus.altasherat.features.login.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.signup.data.repository.local.SignupLocalDS
import java.util.Base64
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
internal class ChangePasswordLocalDS @Inject constructor(private val storageKV: IKeyValueStorageProvider, private val encryptionProvider: IEncryptionProvider):
    IChangePasswordLocalDS {
     override suspend fun getAccessToken(): String {
        val userJsonBase64 = storageKV.getEntry(StorageKeyEnum.ACCESS_TOKEN, "", String::class.java)
        val encryptedBytes = Base64.getDecoder().decode(userJsonBase64)
        val decryptedBytes = encryptionProvider.decryptData(encryptedBytes)
        val decryptedString = decryptedBytes?.decodeToString()
        val result = Gson().fromJson(decryptedString, String::class.java)
        logAccessTokenAfterEncryption(userJsonBase64!!, result)
        return result
    }


    private fun logAccessTokenAfterEncryption(token: String, encryptData: String){
        logger.warning("the Token encryption --> $token ")
        logger.warning("the token after decryption --> $encryptData ")
    }

    companion object {
        private val logger = getClassLogger()
    }

}