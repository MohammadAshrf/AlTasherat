package com.solutionplus.altasherat.features.personalInfo.data.repository.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.personalInfo.domain.repository.local.IUpdateUserLocalDS
import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import java.util.Base64

internal class UpdateUserLocalDS(
    private val storageKV: IKeyValueStorageProvider,
    private val encryptionProvider: IEncryptionProvider
) : IUpdateUserLocalDS {
    override suspend fun hasUserInfo(): Boolean {
        val key = storageKV.hasKey(StorageKeyEnum.USER, false)
        logger.info(key.toString())
        return key
    }

    companion object {
        private val logger = getClassLogger()
    }
}