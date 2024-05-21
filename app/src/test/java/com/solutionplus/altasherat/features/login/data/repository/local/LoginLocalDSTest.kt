package com.solutionplus.altasherat.features.login.data.repository.local

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.login.data.model.entity.UserEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.runner.RunWith
import org.junit.Assert.assertThrows
import java.util.Base64


/* test cases
1. save login with valid response then save encrypted login data
2. save login with empty response then do nothing
4. get access token with no access token is stored then return exception
5.get access token when IV is empty then return exception
6. get access token when stored data cannot be decrypted then return empty string
7. get user then return decrypted user
8. get user with no user is stored then return empty user
9. get user when stored data cannot be decrypted then return empty string
* */
class LoginLocalDSTest{
    private lateinit var storageKV: IKeyValueStorageProvider
    private lateinit var encryptionProvider: IEncryptionProvider
    private lateinit var loginLocalDS: LoginLocalDS

    @Before
    fun setUp() {
        storageKV = mockk()
        encryptionProvider = mockk()
        loginLocalDS = LoginLocalDS(storageKV, encryptionProvider)
    }

    @Test
    fun `when saving user given valid user expect user saved in storage`() = runBlocking {
        val user = UserEntity(id = 1, userName = "testUser", firsName = "Test", lastName = "User")
        val userJson = Gson().toJson(user)
        val bytesUser = userJson.toByteArray()
        val encryptedUserData = "encryptedData"

        coEvery { encryptionProvider.encryptData(bytesUser) } returns encryptedUserData.toByteArray()
        coEvery { storageKV.saveEntry(StorageKeyEnum.USER, any<String>(), String::class.java) } returns Unit

        loginLocalDS.saveUser(user)

        coVerify { storageKV.saveEntry(StorageKeyEnum.USER, Gson().toJson(encryptedUserData), String::class.java) }
    }

    @Test
    fun `when saving access token given valid token expect token saved in storage`() = runBlocking {
        val token = "testToken"
        val bytes = token.toByteArray()
        val encryptedToken = "encryptedToken"

        coEvery { encryptionProvider.encryptData(bytes) } returns encryptedToken.toByteArray()
        coEvery { storageKV.saveEntry(StorageKeyEnum.ACCESS_TOKEN, any<String>(), String::class.java) } returns Unit

        loginLocalDS.saveAccessToken(token)

        coVerify { storageKV.saveEntry(StorageKeyEnum.ACCESS_TOKEN, encryptedToken, String::class.java) }
    }

    @Test
    fun `when getting user expect user returned from storage`() = runBlocking {
        val user = UserEntity(id = 1, userName = "testUser", firsName = "Test", lastName = "User")
        val userJson = Gson().toJson(user)
        val encryptedUserData = Base64.getEncoder().encodeToString(userJson.toByteArray())

        coEvery { storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java) } returns encryptedUserData
        coEvery { encryptionProvider.decryptData(Base64.getDecoder().decode(encryptedUserData)) } returns userJson.toByteArray()

        val result = loginLocalDS.getUser()

        assertEquals(user, result)
    }




    //****************************//

    // Test case 2: Save login with empty response then do nothing
    @Test
    fun `when saving user given empty user expect storage not called`() = runBlocking {
        // Given
        val user = UserEntity() // Empty user

        // Stubbing should not be necessary as the method should not be called

        // When
        loginLocalDS.saveUser(user)

        // Then
        // Ensure that storageKV.saveEntry is not called
    }

    // Test case 4: Get access token with no access token stored then return exception
    @Test
    fun `when getting access token given no token stored expect exception thrown`()  {
         runBlocking {
             // Given
             val encryptedToken = ""

             // Stubbing storageKV
             coEvery {
                 storageKV.getEntry(
                     StorageKeyEnum.ACCESS_TOKEN,
                     "",
                     String::class.java
                 )
             } returns encryptedToken

             // When / Then
             assertThrows(Exception::class.java) {
                 runBlocking {
                     loginLocalDS.getUser()
                 }
             }
         }
    }

    // Test case 5: Get access token when IV is empty then return exception
    @Test
    fun `when getting access token given empty IV expect exception thrown`(){
        runBlocking {
            // Given
            val encryptedToken = "encryptedAccessToken"

            // Stubbing storageKV
            coEvery {
                storageKV.getEntry(
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
                    loginLocalDS.getUser()
                }
            }
        }
    }

    // Test case 6: Get access token when stored data cannot be decrypted then return empty string
    @Test
    fun `when getting user given invalid data expect empty user entity returned`() = runBlocking {
        // Given
        val encryptedUserData = "invalidEncryptedUserData"

        // Stubbing storageKV
        coEvery { storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java) } returns encryptedUserData

        // Stubbing encryptionProvider
        coEvery { encryptionProvider.decryptData(Base64.getDecoder().decode(encryptedUserData)) } returns null

        // When
        val result = loginLocalDS.getUser()

        // Then
        assertEquals(UserEntity(), result)
    }


}