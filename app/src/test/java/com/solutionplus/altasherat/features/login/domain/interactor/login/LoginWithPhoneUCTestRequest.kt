package com.solutionplus.altasherat.features.login.domain.interactor.login


import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.login.domain.model.Login
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserLocalUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import com.solutionplus.altasherat.features.services.user.domain.models.Image
import com.solutionplus.altasherat.features.services.user.domain.models.Phone
import com.solutionplus.altasherat.features.services.user.domain.models.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

/*
- validation
 */
class LoginWithPhoneUCTestRequest {

    private lateinit var repository: ILoginRepository
    private lateinit var saveUserUC: SaveUserUC
    private lateinit var getUserUC: GetUserLocalUC
    private lateinit var loginWithPhoneUC: LoginWithPhoneUC

    @Before
    fun setUp() {
        repository = mockk()
        saveUserUC = mockk()
        getUserUC = mockk()
        loginWithPhoneUC = LoginWithPhoneUC(repository, saveUserUC, getUserUC)

    }

    @Test
    fun `when login is successful_given phone country code and password then user details are returned`() =
        runBlocking {
            // Arrange
            val phone = Phone(countryCode = "0020", number = "100100100", extension = "", id = -1, type = "", holderName = "")
            val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
            val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "password")
            val image = Image(id = 1, type = "profile", path = "http://example.com/image.jpg", title = "Profile Image", updatedAt = "2023-01-01", description = "User profile picture", createdAt = "2023-01-01", main = true, priority = 1)
            val country = Country(id = 1, name = "Egypt", code = "EG", flag = "🇪🇬", currency = "EGP", phoneCode = "+20")
            val userInfo = User(id = 1, username = "userName", email = "email", firstname = "firstName", middleName = "middleName", lastname = "lastName", phone = phone, image = image, birthdate = "1990-01-01", emailVerified = true, phoneVerified = true, blocked = 0, country = country, allPermissions = listOf("READ", "WRITE"))
            val accessToken = "token123"
            val loginResponse = Login(message = "Success", accessToken = accessToken, userInfo = userInfo)

            coEvery { repository.loginWithPhone(loginRequest) } returns loginResponse
            coEvery { repository.saveAccessToken(accessToken) } just runs
            coEvery { saveUserUC.execute(userInfo) } just runs
            coEvery { getUserUC.execute(Unit) } returns userInfo


            // Act
            val result = loginWithPhoneUC.execute(loginRequest)

            // Assert
            assertEquals(userInfo, result)
            assertNotNull(result)
            coVerify {
                repository.loginWithPhone(loginRequest)
                repository.saveAccessToken(accessToken)
                saveUserUC.execute(userInfo)
                getUserUC.execute(Unit)
            }
        }

    @Test
    fun `when login is Failed then throw exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "password")
        val exception = LeonException.Server.InternalServerError(404, "internal server error")

        coEvery { repository.loginWithPhone(loginRequest) } throws exception

        // Act
        var thrownException: LeonException.Server.InternalServerError? = null
        try {
            loginWithPhoneUC.execute(loginRequest)
        } catch (e: LeonException.Server.InternalServerError) {
            thrownException = e
        }

        // Assert
        assertTrue(thrownException is LeonException.Server.InternalServerError)
        assertEquals(exception.message, thrownException?.message)
    }

//-------------------------Validation Tests-----------------------------------------//

    @Test
    fun `when login request has invalid phone number then throw validation exception`() =
        runBlocking {
            // Arrange
            val phoneRequest = PhoneRequest(countryCode = "0020", number = "123")
            val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "password")

            // Act & Assert
            val exception = assertThrows<LeonException.Local.RequestValidation> {
               loginWithPhoneUC.execute(loginRequest)
            }

            // Verify
            assertNotNull(exception)
            assertTrue(exception.errors.containsKey(Validation.PHONE))
        }

    @Test
    fun `when login request has empty phone number then throw validation exception`() = runBlocking {
            // Arrange
            val phoneRequest = PhoneRequest(countryCode = "0020", number = "")
            val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "password")

            // Act & Assert
            val exception = assertThrows<LeonException.Local.RequestValidation> {
                  loginWithPhoneUC.execute(loginRequest)
            }

            // Verify
            assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.PHONE))
    }

    @Test
    fun `when login request has invalid password then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "12345")

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
             loginWithPhoneUC.execute(loginRequest)
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.PASSWORD))
    }

    @Test
    fun `when login request has empty password then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "")

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
              loginWithPhoneUC.execute(loginRequest)
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.PASSWORD))
    }


//    @Test
//    fun `test invalid phone number`() = runBlocking {
//        // Arrange
//        val phoneRequest =
//            PhoneRequest(countryCode = "0020", number = "123") // Invalid phoneRequest number
//        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "password")
//
//        // Define behavior for loginWithPhone method
//        coEvery { repository.loginWithPhone(loginRequest) } throws LeonException.Local.RequestValidation(
//            LoginRequest::class,
//            "PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long."
//        )
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            loginWithPhoneUC.execute(loginRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals(
//                "PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long.",
//                e.message
//            )
//        }
//        assertTrue(exceptionThrown)
//    }

//    @Test
//    fun `test invalid phone number is empty`() = runBlocking {
//        // Arrange
//        val phoneRequest = PhoneRequest(countryCode = "0020", number = "")
//        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "password")
//
//        // Define behavior for loginWithPhone method
//        coEvery { repository.loginWithPhone(loginRequest) } throws LeonException.Local.RequestValidation(
//            LoginRequest::class,
//            "PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long."
//        )
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            loginWithPhoneUC.execute(loginRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals(
//                "PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long.",
//                e.message
//            )
//        }
//        assertTrue(exceptionThrown)
//    }
//
//    @Test
//    fun `test invalid password`() = runBlocking {
//        // Arrange
//        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
//        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "12345")
//        // Define behavior for loginWithPhone method
//        coEvery { repository.loginWithPhone(loginRequest) } throws LeonException.Local.RequestValidation(
//            LoginRequest::class,
//            "Password is invalid. It must be between 8 and 50 characters."
//        )
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            loginWithPhoneUC.execute(loginRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals("Password is invalid. It must be between 8 and 50 characters.", e.message)
//        }
//        assertTrue(exceptionThrown)
//    }
//
//    @Test
//    fun `test invalid password is empty`() = runBlocking {
//        // Arrange
//        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
//        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "")
//
//        coEvery { repository.loginWithPhone(loginRequest) } throws LeonException.Local.RequestValidation(
//            LoginRequest::class,
//            "Password is invalid. It must be between 8 and 50 characters."
//        )
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            loginWithPhoneUC.execute(loginRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals(R.string.invalid_password, e.message)
//        }
//        assertTrue(exceptionThrown)
//    }
}
