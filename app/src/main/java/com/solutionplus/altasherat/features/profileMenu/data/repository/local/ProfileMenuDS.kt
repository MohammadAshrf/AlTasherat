package com.solutionplus.altasherat.features.profileMenu.data.repository.local

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.repository.local.IProfileMenuDS
import com.solutionplus.altasherat.features.services.country.data.repository.local.CountriesLocalDS


internal class ProfileMenuDS(
    private val storageKV: IKeyValueStorageProvider,
    private val encryptionProvider: IEncryptionProvider
) :
    IProfileMenuDS {

    override suspend fun getUser(): UserEntity {
        if (!storageKV.hasKey(StorageKeyEnum.USER, String::class.java)) {
            return UserEntity(-1,"","","","","","","","",false,"")
        }

        val userJson = storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java)
        logger.info("userInfo before decryption --> $userJson ")

        if (userJson.isNullOrEmpty()) {
            return UserEntity(-1,"","","","","","","","",false , "")
        }

        val decodedBytes: ByteArray = Base64.decode(userJson, Base64.DEFAULT)
        val decryptedBytes = decodedBytes.let { encryptionProvider.decryptData(it)?.decodeToString() }

        val result = decryptedBytes.let { Gson().fromJson(it, UserEntity::class.java) }
        logger.warning("userInfo after decryption --> $result ")

        return result
    }


    companion object {
        val logger = getClassLogger()
    }
}