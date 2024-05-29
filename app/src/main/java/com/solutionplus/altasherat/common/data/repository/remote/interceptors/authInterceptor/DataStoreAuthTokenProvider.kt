package com.solutionplus.altasherat.common.data.repository.remote.interceptors.authInterceptor

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import java.util.Base64
import javax.inject.Inject

class DataStoreAuthTokenProvider(
    private val storageKV: IKeyValueStorageProvider,
    private val encryptionProvider: IEncryptionProvider
) : AuthTokenProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAuthToken(): String? {
        logger.info("getAuthToken called from DataStoreAuthTokenProvider")
        val userJsonBase64 = storageKV.getEntry(StorageKeyEnum.ACCESS_TOKEN, "", String::class.java)
        if (userJsonBase64.isNullOrEmpty()) {
            return null
        }
        val encryptedBytes = Base64.getDecoder().decode(userJsonBase64)
        val decryptedBytes = encryptionProvider.decryptData(encryptedBytes)
        val decryptedString = decryptedBytes?.decodeToString()
        val result = Gson().fromJson(decryptedString, String::class.java)
        logger.info("getAuthToken result --> $result")
        return result
    }
    companion object {
        private val logger = getClassLogger()
    }
}