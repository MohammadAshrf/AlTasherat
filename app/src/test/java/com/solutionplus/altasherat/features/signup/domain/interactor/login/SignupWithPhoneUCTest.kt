package com.solutionplus.altasherat.features.signup.domain.interactor.login

import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.signup.data.mapper.UserMapper
import com.solutionplus.altasherat.features.signup.data.mapper.UserMapper.domainToEntity
import com.solutionplus.altasherat.features.signup.data.model.request.Phone
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.model.Signup
import com.solutionplus.altasherat.features.signup.domain.model.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import com.solutionplus.altasherat.features.signup.domain.usecase.SignupUC
import kotlinx.coroutines.test.runBlockingTest

/*
- validation
 */
 class SignupWithPhoneUCTest {

    private lateinit var repository: ISignupRepository
    private lateinit var signupUC: SignupUC

    @Before
    fun setUp() {
        repository = mockk()
        signupUC = SignupUC(repository)
    }

    @Test
    fun `when signup is successful_ then user details are returned`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = "100100100"
        )
        val signupRequest = SignupRequest(phone = phone, password = "123456789", countryCode = "0020", countryId = 1,  firstName = "mahmoud", lastName = "Abdo", passwordConfirmation ="123456789")
        val userInfo = User(
            id = 1,
            userName = "jdoe",
            fullName = "John Doe",
            email = "jdoe@example.com",
            phone = phone.number
        )
        val accessToken = "token123"
        val signup = Signup(message = "Success", token = "token123", user = userInfo)

        coEvery { repository.signupWithPhone(signupRequest) } returns signup
        coEvery { repository.saveUser(userInfo) } just Runs
        coEvery { repository.saveAccessToken(accessToken) } just Runs
        coEvery { repository.getUser() } returns domainToEntity(userInfo)

        // Act
        val result = signupUC.execute(signupRequest)

        // Assert
        assertEquals(userInfo, result)
        assertNotNull(result)
        coVerify {
            repository.signupWithPhone(signupRequest)
            repository.saveUser(userInfo)
            repository.saveAccessToken(accessToken)
            repository.getUser()
        }
    }

    @Test
    fun `when signup is Failed then throw exception`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = "100100100"
        )
        val signupRequest = SignupRequest(phone = phone,firstName = "bnm", lastName = "name", password = "password")

        val exception = LeonException.Server.InternalServerError(404, "internal server error")

        coEvery { repository.signupWithPhone(signupRequest) } throws exception

        // Act
        var thrownException: LeonException.Server.InternalServerError? = null
        try {
            signupUC.execute(signupRequest)
        } catch (e: LeonException.Server.InternalServerError) {
            thrownException = e
        }

        // Assert
        assertTrue(thrownException is LeonException.Server.InternalServerError)
        assertEquals(exception.message, thrownException?.message)
    }

    //------------------------------Validation------------------------------------//
    @Test
    fun `test invalid first name`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = "12345678"
        )
        val signupRequest = SignupRequest(phone = phone,firstName = "nn", lastName = "name", password = "password")

        // Act & Assert
        var exceptionThrown = false
        try {
            signupUC.execute(signupRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("First name is invalid. It must be between 3 and 15 characters.", e.message)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `test invalid first name is empty`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = ""
        )
        val signupRequest = SignupRequest(phone = phone,firstName = "", lastName = "name", password = "password")

        // Act & Assert
        var exceptionThrown = false
        try {
            signupUC.execute(signupRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("First name is invalid. It must be between 3 and 15 characters.", e.message)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `test invalid last name`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = "12345678"
        )
        val signupRequest = SignupRequest(phone = phone,firstName = "name", lastName = "mm", password = "password")

        // Act & Assert
        var exceptionThrown = false
        try {
            signupUC.execute(signupRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("Last name is invalid. It must be between 3 and 15 characters.", e.message)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `test invalid last name is empty`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = ""
        )
        val signupRequest = SignupRequest(phone = phone, firstName = "name", lastName = "",password = "password")

        // Act & Assert
        var exceptionThrown = false
        try {
            signupUC.execute(signupRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("Last name is invalid. It must be between 3 and 15 characters.", e.message)
        }
        assertTrue(exceptionThrown)
    }
    @Test
    fun `test invalid phone number`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = "12345678"
        )
        val signupRequest = SignupRequest(phone = phone,firstName = "name", lastName = "name", password = "password")

        // Act & Assert
        var exceptionThrown = false
        try {
            signupUC.execute(signupRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("Phone number is invalid. It must contain only digits and be between 9 and 15 characters long.", e.message)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `test invalid phone number is empty`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = ""
        )
        val signupRequest = SignupRequest(phone = phone,firstName = "name", lastName = "name", password = "password")

        // Act & Assert
        var exceptionThrown = false
        try {
            signupUC.execute(signupRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("Phone number is invalid. It must contain only digits and be between 9 and 15 characters long.", e.message)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `test invalid password`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = "100100100"
        )
        val signupRequest = SignupRequest(phone = phone,firstName = "name", lastName = "name", password = "12345")

        // Act & Assert
        var exceptionThrown = false
        try {
            signupUC.execute(signupRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("Password is invalid. It must be between 8 and 50 characters.", e.message)
        }
        assertTrue(exceptionThrown)
    }

    @Test
    fun `test invalid password is empty`() = runBlocking {
        // Arrange
        val phone = Phone(
            countryCode = "0020",
            number = "100100100"
        )
        val signupRequest = SignupRequest(phone = phone, firstName = "name", lastName = "name", password = "")

        // Act & Assert
        var exceptionThrown = false
        try {
            signupUC.execute(signupRequest)
        } catch (e: LeonException.Local.RequestValidation) {
            exceptionThrown = true
            assertEquals("Password is invalid. It must be between 8 and 50 characters.", e.message)
        }
        assertTrue(exceptionThrown)
    }
}
