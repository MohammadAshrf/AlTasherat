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
@ExperimentalCoroutinesApi
class ChangePasswordLocalDSTest{


    private lateinit var localDataSource: ChangePasswordLocalDS
    private val storageProvider: IKeyValueStorageProvider = mockk()
    private val encryptionProvider: IEncryptionProvider = mockk()

    @Before
    fun setUp() {
        localDataSource = ChangePasswordLocalDS(storageProvider, encryptionProvider)
    }

    @Test
    fun `when getting access token given no token stored expect exception thrown`()  {
        runBlocking {
            // Given
            val encryptedToken = ""

            // Stubbing storageKV
            coEvery {
                storageProvider.getEntry(
                    StorageKeyEnum.ACCESS_TOKEN,
                    "",
                    String::class.java
                )
            } returns encryptedToken

            // When / Then
            assertThrows(Exception::class.java) {
                runBlocking {
                    localDataSource.getAccessToken()
                }
            }
        }
    }

    @Test
    fun `when getting access token given empty IV expect exception thrown`(){
        runBlocking {
            // Given
            val encryptedToken = "encryptedAccessToken"

            // Stubbing storageKV
            coEvery {
                storageProvider.getEntry(
                    StorageKeyEnum.ACCESS_TOKEN,
                    "",
                    String::class.java
                )
            } returns encryptedToken

            // Stubbing encryptionProvider
            coEvery { encryptionProvider.decryptData(encryptedToken.toByteArray()) } returns null

            // When / Then
            assertThrows(Exception::class.java) {
                runBlocking {
                    localDataSource.getAccessToken()
                }
            }
        }
    }

//    @Test
//    fun `when access token is stored, expect returned access token `() = runTest {
//        // Arrange
//        val accessToken = "mock_access_token"
//        coEvery { storageProvider.getEntry<String>(any(), any(), any()) } returns accessToken
//
//        // Act
//        val result = localDataSource.getAccessToken()
//
//        // Assert
//        assertEquals(accessToken, result)
//    }
//
//    @Test
//    fun `when no access token is stored, expect returned null`() = runTest {
//        // Arrange
//        coEvery { storageProvider.getEntry<String>(any(), any(), any()) } returns null
//
//        // Act
//        val result = localDataSource.getAccessToken()
//
//        // Assert
//        assertNull(result)
//    }
//
//    @Test
//    fun `when exception occurs during retrieval, expect returned null`() = runTest {
//        // Arrange
//        coEvery { storageProvider.getEntry<String>(any(), any(), any()) } throws LeonException.Local.IOOperation(0, "Storage error")
//
//        // Act
//        val result = localDataSource.getAccessToken()
//
//        // Assert
//        assertNull(result)
//    }

}