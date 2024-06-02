package com.solutionplus.altasherat.features.login.domain.interactor.login


import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.mapper.UserMapper
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.login.domain.model.Login
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
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
- validation
 */
class LoginWithPhoneUCTestRequest {

    private lateinit var repository: ILoginRepository
    private lateinit var loginWithPhoneUC: LoginWithPhoneUC
    @Before
    fun setUp() {
        repository = mockk()
        loginWithPhoneUC = LoginWithPhoneUC(repository)

    }

    @Test
    fun `when login is successful_given phone country code and password then user details are returned`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "password")
        val userInfo = User(
            id = 1,
            userName = "fakeUser",
            fullName = "Fake User",
            email = "fake.user@example.com",
            firstName = "Fake",
            middleName = "User",
            lastName = "Example",
            phone = phoneRequest.toString(),
            birthDate = "2000-01-01",
            imageUrl = "http://example.com/image.jpg",
            emailVerified = true
        )
        val accessToken = "token123"
        val loginResponse = Login(message = "Success", accessToken = accessToken, userInfo = userInfo)

        coEvery { repository.loginWithPhone(loginRequest) } returns loginResponse
        coEvery { repository.saveUser(userInfo) } just Runs
        coEvery { repository.saveAccessToken(accessToken) } just Runs
        coEvery { repository.getUser() } returns UserMapper.domainToEntity(userInfo)

        // Act
        val result = loginWithPhoneUC.execute(loginRequest)

        // Assert
        assertEquals(userInfo, result)
        assertNotNull(result)
        coVerify {
            repository.loginWithPhone(loginRequest)
            repository.saveUser(userInfo)
            repository.saveAccessToken(accessToken)
            repository.getUser()
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
    fun `test invalid phone number`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "123") // Invalid phoneRequest number
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "password")

        // Define behavior for loginWithPhone method
        coEvery { repository.loginWithPhone(loginRequest) } throws LeonException.Local.RequestValidation(LoginRequest::class, "PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long.")

        // Act & Assert
        var exceptionThrown = false
        try {
            loginWithPhoneUC.execute(loginRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long.", e.message)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `test invalid phone number is empty`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "password")

        // Define behavior for loginWithPhone method
        coEvery { repository.loginWithPhone(loginRequest) } throws LeonException.Local.RequestValidation(LoginRequest::class, "PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long.")

        // Act & Assert
        var exceptionThrown = false
        try {
            loginWithPhoneUC.execute(loginRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long.", e.message)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `test invalid password`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "12345")
        // Define behavior for loginWithPhone method
        coEvery { repository.loginWithPhone(loginRequest) } throws LeonException.Local.RequestValidation(LoginRequest::class, "Password is invalid. It must be between 8 and 50 characters.")

        // Act & Assert
        var exceptionThrown = false
        try {
            loginWithPhoneUC.execute(loginRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("Password is invalid. It must be between 8 and 50 characters.", e.message)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `test invalid password is empty`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "")

        coEvery { repository.loginWithPhone(loginRequest) } throws LeonException.Local.RequestValidation(LoginRequest::class, "Password is invalid. It must be between 8 and 50 characters.")

        // Act & Assert
        var exceptionThrown = false
        try {
            loginWithPhoneUC.execute(loginRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("Password is invalid. It must be between 8 and 50 characters.", e.message)
        }
        assertTrue(exceptionThrown)
    }


}
