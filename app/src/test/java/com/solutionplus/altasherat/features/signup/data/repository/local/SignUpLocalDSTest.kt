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
        loginLocalDS.saveUser(UserMapper.domainToEntity(user))

        // Assert
        coVerify { storageKV.saveEntry(StorageKeyEnum.USER, encryptUserDataBase64, String::class.java) }
        coVerify { storageKV.saveEntry(StorageKeyEnum.IS_USER_LOGGED_IN, true, Boolean::class.java) }
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
        val phoneRequest = Phone(countryCode = "0020", number = "100100100", extension = "", id = -1, type = "", holderName = "")
        val image = Image(id = 1, type = "profile", path = "http://example.com/image.jpg", title = "Profile Image", updatedAt = "2023-01-01", description = "User profile picture", createdAt = "2023-01-01", main = true, priority = 1)
        val country = Country(id = 1, name = "Egypt", code = "EG", flag = "🇪🇬", currency = "EGP", phoneCode = "+20")
        val user = User(id = 1, username = "userName", email = "email", firstname = "firstName", middleName = "middleName", lastname = "lastName", phone = phoneRequest, image = image, birthdate = "1990-01-01", emailVerified = true, phoneVerified = true, blocked = 0, country = country, allPermissions = listOf("READ", "WRITE"))

        val userJson = Gson().toJson(user)
        val encryptedUserData = Base64.getEncoder().encodeToString(userJson.toByteArray())

        coEvery { storageKV.getEntry(StorageKeyEnum.USER, "", String::class.java) } returns encryptedUserData
        coEvery { encryptionProvider.decryptData(Base64.getDecoder().decode(encryptedUserData)) } returns userJson.toByteArray()

        val result = loginLocalDS.getUser()

        assertEquals(UserMapper.domainToEntity(user), result)
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

            coEvery { encryptionProvider.decryptData(encryptedToken.toByteArray()) } returns null

            // When / Then
            assertThrows(Exception::class.java) {
                runBlocking {
                    loginLocalDS.getUser()
                }
            }
        }
    }


}