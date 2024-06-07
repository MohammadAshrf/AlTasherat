package com.solutionplus.altasherat.features.login.data.repository.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
internal class LoginLocalDS (private val storageKV: IKeyValueStorageProvider,private val encryptionProvider: IEncryptionProvider):
    ILoginLocalDS {

    override suspend fun saveAccessToken(token: String) {
        val bytes = token.toByteArray()
        val encryptedData = encryptionProvider.encryptData(bytes)
        val encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedData)
        logAccessTokenAfterEncryption(token, encryptedDataBase64)
        storageKV.saveEntry(StorageKeyEnum.ACCESS_TOKEN, encryptedDataBase64, String::class.java)
    }

    private fun logAccessTokenAfterEncryption( token: String,encryptData: String){
        logger.warning("the Token is --> $token ")
        logger.warning("the token after encryption --> $encryptData ")
    }

    companion object {
        private val logger = getClassLogger()
    }
}