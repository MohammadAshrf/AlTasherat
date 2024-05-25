package com.solutionplus.altasherat.features.changepassword.data.repository.local

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Type
import com.google.gson.Gson
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.runBlocking
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import java.util.*

/*
test get access token after decryption then return successfully or true
 */
@ExperimentalCoroutinesApi
class ChangePasswordLocalDSTest {


    private lateinit var changePasswordLocalDS: ChangePasswordLocalDS
    private val storageKV: IKeyValueStorageProvider = mockk()
    private val encryptionProvider: IEncryptionProvider = mockk()

    @Before
    fun setUp() {
        changePasswordLocalDS = ChangePasswordLocalDS(storageKV, encryptionProvider)
    }


    @Test
    fun `test get access token after decryption then return successfully`() = runBlocking {
        // Arrange
        val accessToken = "bkbakdmbkmbkmboambpamam"
        val encryptedBytes = accessToken.toByteArray()
        val encryptedString = Base64.getEncoder().encodeToString(encryptedBytes)

        coEvery {
            storageKV.getEntry(
                StorageKeyEnum.ACCESS_TOKEN,
                "",
                String::class.java
            )
        } returns encryptedString
        every {
            encryptionProvider.decryptData(
                Base64.getDecoder().decode(encryptedString)
            )
        } returns encryptedBytes

        // Act
        val result = changePasswordLocalDS.getAccessToken()

        // Assert
        assertNotNull(result)
        assertEquals(accessToken, result)
    }

}