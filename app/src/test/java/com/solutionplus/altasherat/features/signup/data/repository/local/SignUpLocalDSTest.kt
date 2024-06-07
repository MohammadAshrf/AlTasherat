package com.solutionplus.altasherat.features.signup.data.repository.local

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.domain.models.Image
import com.solutionplus.altasherat.features.services.user.domain.models.Phone
import com.solutionplus.altasherat.features.services.user.domain.models.User
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertThrows
import java.util.Base64


/* test cases
1. test save user info after encryptData`
2. test save token after encryptData
4. test getting user info after decrypted
5. test getting token
* */
class SignUpLocalDSTest{
    private lateinit var storageKV: IKeyValueStorageProvider
    private lateinit var encryptionProvider: IEncryptionProvider
    private lateinit var loginLocalDS: SignupLocalDS

    @Before
    fun setUp() {
        storageKV = mockk()
        encryptionProvider = mockk()
        loginLocalDS = SignupLocalDS(storageKV, encryptionProvider)
    }


    @Test
    fun `test save token after encryptData`() = runBlocking {
        // Arrange
        val token = "myAccessToken"
        val bytes = token.toByteArray()
        val encryptedData = "encryptedData".toByteArray()
        val encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedData)

        every { encryptionProvider.encryptData(bytes) } returns encryptedData
        coEvery { storageKV.saveEntry(StorageKeyEnum.ACCESS_TOKEN, encryptedDataBase64, String::class.java) } just Runs

        // Act
        loginLocalDS.saveAccessToken(token)

        // Assert
        coVerify { storageKV.saveEntry(StorageKeyEnum.ACCESS_TOKEN, encryptedDataBase64, String::class.java) }
        verify { encryptionProvider.encryptData(bytes) }
    }



    // Test case 4: Get access token with no access token stored then return exception
//    @Test
//    fun `when getting access token given no token stored expect exception thrown`()  {
//         runBlocking {
//             // Given
//             val encryptedToken = ""
//
//             // Stubbing storageKV
//             coEvery {
//                 storageKV.getEntry(
//                     StorageKeyEnum.ACCESS_TOKEN,
//                     "",
//                     String::class.java
//                 )
//             } returns encryptedToken
//
//             // When / Then
//             assertThrows(Exception::class.java) {
//                 runBlocking {
//                     loginLocalDS.getUser()
//                 }
//             }
//         }
//    }

    // Test case 5: Get access token when IV is empty then return exception
//    @Test
//    fun `when getting access token given empty IV expect exception thrown`(){
//        runBlocking {
//            // Given
//            val encryptedToken = "encryptedAccessToken"
//
//            // Stubbing storageKV
//            coEvery {
//                storageKV.getEntry(
//                    StorageKeyEnum.ACCESS_TOKEN,
//                    "",
//                    String::class.java
//                )
//            } returns encryptedToken
//
//            coEvery { encryptionProvider.decryptData(encryptedToken.toByteArray()) } returns null
//
//            // When / Then
//            assertThrows(Exception::class.java) {
//                runBlocking {
//                    loginLocalDS.getUser()
//                }
//            }
//        }
//    }


}