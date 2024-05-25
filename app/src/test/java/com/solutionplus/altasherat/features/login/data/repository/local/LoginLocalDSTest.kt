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
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.junit.Assert.assertThrows
import java.util.Base64


/* test cases
1.test save user info after encryptData
2. test save token after encryptData
3. test get user after decrypt data
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
    fun `test save user info after encryptData`() = runBlocking {
        // Arrange
        val user = UserEntity(1, "username", "email@example.com")
        val userJson = Gson().toJson(user)
        val bytesUser = userJson.toByteArray()
        val encryptedUserData = "encryptedUserData".toByteArray()
        val encryptUserDataBase64 = Base64.getEncoder().encodeToString(encryptedUserData)

        every { encryptionProvider.encryptData(bytesUser) } returns encryptedUserData
        coEvery { storageKV.saveEntry(StorageKeyEnum.USER, encryptUserDataBase64, String::class.java) } just Runs

        // Act
        loginLocalDS.saveUser(user)

        // Assert
        coVerify { storageKV.saveEntry(StorageKeyEnum.USER, encryptUserDataBase64, String::class.java) }
        verify { encryptionProvider.encryptData(bytesUser) }
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

}