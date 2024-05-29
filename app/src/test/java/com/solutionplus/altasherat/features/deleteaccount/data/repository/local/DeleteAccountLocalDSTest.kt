package com.solutionplus.altasherat.features.deleteaccount.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.login.data.repository.local.LoginLocalDS
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/*
   test delete UserInfo
   test delete Access token
 */
class DeleteAccountLocalDSTest{
    private lateinit var storageKV: IKeyValueStorageProvider
    private lateinit var deleteAccountLocalDS: DeleteAccountLocalDS

    @Before
    fun setUp() {
        storageKV = mockk()
        deleteAccountLocalDS = DeleteAccountLocalDS(storageKV)
    }

    @Test
    fun `test delete User Info`() = runBlocking {
        // Arrange
        coEvery { storageKV.deleteEntry<String>(any(), any()) } just Runs

        // Act
        deleteAccountLocalDS.deleteUser()

        // Assert
        coVerify { storageKV.deleteEntry<String>(StorageKeyEnum.USER, String::class.java) }
    }

    @Test
    fun ` test delete Access token `() = runBlocking {
        // Arrange
        coEvery { storageKV.deleteEntry<String>(any(), any()) } just Runs

        // Act
        deleteAccountLocalDS.deleteAccessToken()

        // Assert
        coVerify { storageKV.deleteEntry<String>(StorageKeyEnum.ACCESS_TOKEN, String::class.java) }
    }

}
