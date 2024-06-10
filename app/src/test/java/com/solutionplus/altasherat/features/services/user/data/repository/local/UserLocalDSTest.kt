package com.solutionplus.altasherat.features.services.user.data.repository.local

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.domain.models.Image
import com.solutionplus.altasherat.features.services.user.domain.models.Phone
import com.solutionplus.altasherat.features.services.user.domain.models.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.Runs
import java.util.Base64

/* test cases
1. test save user info after encryptData`
4. test getting user info after decrypted
* */
class UserLocalDSTest{

    private lateinit var storageKV: IKeyValueStorageProvider
    private lateinit var encryptionProvider: IEncryptionProvider
    private lateinit var userLocalDS: UserLocalDS

    @Before
    fun setUp() {
        storageKV = mockk()
        encryptionProvider = mockk()
        userLocalDS = UserLocalDS(storageKV, encryptionProvider)
    }

        @Test
    fun `test save user info after encryptData`() = runBlocking {
        // Arrange
        val phoneRequest = Phone(countryCode = "0020", number = "100100100", extension = "", id = -1, type = "", holderName = "")
        val image = Image(id = 1, type = "profile", path = "http://example.com/image.jpg", title = "Profile Image", updatedAt = "2023-01-01", description = "User profile picture", createdAt = "2023-01-01", main = true, priority = 1)
        val country = Country(id = 1, name = "Egypt", code = "EG", flag = "🇪🇬", currency = "EGP", phoneCode = "+20")
        val user = User(id = 1, username = "userName", email = "email", firstname = "firstName", middleName = "middleName", lastname = "lastName", phone = phoneRequest, image = image, birthdate = "1990-01-01", emailVerified = true, phoneVerified = true, blocked = 0, country = country, allPermissions = listOf("READ", "WRITE"))

        val userJson = Gson().toJson(user)
        val bytesUser = userJson.toByteArray()
        val encryptedUserData = "encryptedUserData".toByteArray()
        val encryptUserDataBase64 = Base64.getEncoder().encodeToString(encryptedUserData)

        every { encryptionProvider.encryptData(bytesUser) } returns encryptedUserData
        coEvery { storageKV.saveEntry(StorageKeyEnum.USER, encryptUserDataBase64, String::class.java) } just Runs
        coEvery{ storageKV.saveEntry(StorageKeyEnum.IS_USER_LOGGED_IN, true, Boolean::class.java)} just  Runs
        // Act
        userLocalDS.saveUser(UserMapper.domainToEntity(user))

        // Assert
        coVerify { storageKV.saveEntry(StorageKeyEnum.USER, encryptUserDataBase64, String::class.java) }
        coVerify { storageKV.saveEntry(StorageKeyEnum.IS_USER_LOGGED_IN, true, Boolean::class.java) }
        verify { encryptionProvider.encryptData(bytesUser) }
    }

        @Test
    fun `when getting user expect user returned from storage`() = runBlocking {
        val phoneRequest = Phone(countryCode = "0020", number = "100100100", extension = "", id = -1, type = "", holderName = "")
        val image = Image(id = 1, type = "profile", path = "http://example.com/image.jpg", title = "Profile Image", updatedAt = "2023-01-01", description = "User profile picture", createdAt = "2023-01-01", main = true, priority = 1)
        val country = Country(id = 1, name = "Egypt", code = "EG", flag = "🇪🇬", currency = "EGP", phoneCode = "+20")
        val user = User(id = 1, username = "userName", email = "email", firstname = "firstName", middleName = "middleName", lastname = "lastName", phone = phoneRequest, image = image, birthdate = "1990-01-01", emailVerified = true, phoneVerified = true, blocked = 0, country = country, allPermissions = listOf("READ", "WRITE"))

        val userJson = Gson().toJson(user)
        val encryptedUserData = Base64.getEncoder().encodeToString(userJson.toByteArray())

        coEvery { storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java) } returns encryptedUserData
        coEvery { encryptionProvider.decryptData(Base64.getDecoder().decode(encryptedUserData)) } returns userJson.toByteArray()

        val result = userLocalDS.getUserLocal()

        assertEquals(UserMapper.domainToEntity(user), result)
    }
}