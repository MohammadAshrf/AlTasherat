package com.solutionplus.altasherat.features.signup.domain.interactor.login

import com.solutionplus.altasherat.R
import io.mockk.*
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper.domainToEntity
import com.solutionplus.altasherat.features.signup.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.services.user.domain.models.Image
import com.solutionplus.altasherat.features.services.user.domain.models.Phone
import com.solutionplus.altasherat.features.signup.domain.model.Signup
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import com.solutionplus.altasherat.features.signup.domain.usecase.SignupUC
import org.junit.jupiter.api.assertThrows

/*
- validation
 */
 class SignupWithPhoneUCTestRequest {

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
        val phone = Phone(countryCode = "0020", number = "100100100", extension = "", id = -1, type = "", holderName = "")
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val signupRequest = SignupRequest(  firstName = "John", lastName = "Doe", email = "john.doe@example.com",phone = phoneRequest, password = "password")
        val image = Image(id = 1, type = "profile", path = "http://example.com/image.jpg", title = "Profile Image", updatedAt = "2023-01-01", description = "User profile picture", createdAt = "2023-01-01", main = true, priority = 1)
        val country = Country(id = 1, name = "Egypt", code = "EG", flag = "🇪🇬", currency = "EGP", phoneCode = "+20")
        val userInfo = User(id = 1, username = "userName", email = "email", firstname = "firstName", middleName = "middleName", lastname = "lastName", phone = phone, image = image, birthdate = "1990-01-01", emailVerified = true, phoneVerified = true, blocked = 0, country = country, allPermissions = listOf("READ", "WRITE"))
        val accessToken = "token123"
        val signup = Signup(message = "Success", token = accessToken, user = userInfo)

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
        val phone = PhoneRequest(countryCode = "0020", number = "100100100")
        val signupRequest = SignupRequest(phone = phone, firstName = "John", lastName = "Doe", email = "john.doe@example.com", password = "password")
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
    fun `when signup request has invalid first name then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(
            firstName = "Jo", // Invalid: too short
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = PhoneRequest(countryCode = "0020", number = "100100100"),
            password = "password",
            passwordConfirmation = "password",
            countryId = 1,
            countryCode = "+20"
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            runBlocking { signupUC.execute(signupRequest) }
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.message!!.contains(R.string.invalid_first_name.toString()))
    }

    @Test
    fun `when signup request has empty last name then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(
            firstName = "John",
            lastName = "", // Invalid: empty
            email = "john.doe@example.com",
            phone = PhoneRequest(countryCode = "0020", number = "100100100"),
            password = "password",
            passwordConfirmation = "password",
            countryId = 1,
            countryCode = "+20"
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            runBlocking { signupUC.execute(signupRequest) }
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.message!!.contains(R.string.invalid_last_name.toString()))
    }

    @Test
    fun `when signup request has invalid email then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(
            firstName = "John",
            lastName = "Doe",
            email = "", // Invalid: empty
            phone = PhoneRequest(countryCode = "0020", number = "100100100"),
            password = "password",
            passwordConfirmation = "password",
            countryId = 1,
            countryCode = "+20"
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            runBlocking { signupUC.execute(signupRequest) }
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.message!!.contains(R.string.invalid_email.toString()))
    }

    @Test
    fun `when signup request has invalid phone number then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "", number = "")
        val signupRequest = SignupRequest(
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = phoneRequest, // Invalid: empty
            password = "password",
            passwordConfirmation = "password",
            countryId = 1,
            countryCode = "+20"
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            runBlocking { signupUC.execute(signupRequest) }
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.message!!.contains(R.string.invalid_phone.toString()))
    }

    @Test
    fun `when signup request has invalid password then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            phone = PhoneRequest(countryCode = "0020", number = "100100100"),
            password = "short", // Invalid: too short
            passwordConfirmation = "short",
            countryId = 1,
            countryCode = "+20"
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            runBlocking { signupUC.execute(signupRequest) }
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.message!!.contains(R.string.invalid_password.toString()))
    }

//    @Test
//    fun `test invalid first name`() = runBlocking {
//        // Arrange
//        val phone = PhoneRequest(
//            countryCode = "0020",
//            number = "12345678"
//        )
//        val signupRequest = SignupRequest(phone = phone,firstName = "nn", lastName = "name", password = "password")
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            signupUC.execute(signupRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals("First name is invalid. It must be between 3 and 15 characters.", e.message)
//        }
//        assertTrue(exceptionThrown)
//    }
//
//    @Test
//    fun `test invalid first name is empty`() = runBlocking {
//        // Arrange
//        val phone = PhoneRequest(
//            countryCode = "0020",
//            number = ""
//        )
//        val signupRequest = SignupRequest(phone = phone,firstName = "", lastName = "name", password = "password")
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            signupUC.execute(signupRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals("First name is invalid. It must be between 3 and 15 characters.", e.message)
//        }
//        assertTrue(exceptionThrown)
//    }
//
//    @Test
//    fun `test invalid last name`() = runBlocking {
//        // Arrange
//        val phone = PhoneRequest(
//            countryCode = "0020",
//            number = "12345678"
//        )
//        val signupRequest = SignupRequest(phone = phone,firstName = "name", lastName = "mm", password = "password")
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            signupUC.execute(signupRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals("Last name is invalid. It must be between 3 and 15 characters.", e.message)
//        }
//        assertTrue(exceptionThrown)
//    }
//
//    @Test
//    fun `test invalid last name is empty`() = runBlocking {
//        // Arrange
//        val phone = PhoneRequest(
//            countryCode = "0020",
//            number = ""
//        )
//        val signupRequest = SignupRequest(phone = phone, firstName = "name", lastName = "",password = "password")
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            signupUC.execute(signupRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals("Last name is invalid. It must be between 3 and 15 characters.", e.message)
//        }
//        assertTrue(exceptionThrown)
//    }
//    @Test
//    fun `test invalid phone number`() = runBlocking {
//        // Arrange
//        val phone = PhoneRequest(
//            countryCode = "0020",
//            number = "12345678"
//        )
//        val signupRequest = SignupRequest(phone = phone,firstName = "name", lastName = "name", password = "password")
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            signupUC.execute(signupRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals("PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long.", e.message)
//        }
//        assertTrue(exceptionThrown)
//    }
//
//    @Test
//    fun `test invalid phone number is empty`() = runBlocking {
//        // Arrange
//        val phone = PhoneRequest(
//            countryCode = "0020",
//            number = ""
//        )
//        val signupRequest = SignupRequest(phone = phone,firstName = "name", lastName = "name", password = "password")
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            signupUC.execute(signupRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals("PhoneRequest number is invalid. It must contain only digits and be between 9 and 15 characters long.", e.message)
//        }
//        assertTrue(exceptionThrown)
//    }
//
//    @Test
//    fun `test invalid password`() = runBlocking {
//        // Arrange
//        val phone = PhoneRequest(
//            countryCode = "0020",
//            number = "100100100"
//        )
//        val signupRequest = SignupRequest(phone = phone,firstName = "name", lastName = "name", password = "12345")
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            signupUC.execute(signupRequest)
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
//        val phone = PhoneRequest(
//            countryCode = "0020",
//            number = "100100100"
//        )
//        val signupRequest = SignupRequest(phone = phone, firstName = "name", lastName = "name", password = "")
//
//        // Act & Assert
//        var exceptionThrown = false
//        try {
//            signupUC.execute(signupRequest)
//        } catch (e: LeonException.Local.RequestValidation) {
//            exceptionThrown = true
//            assertEquals("Password is invalid. It must be between 8 and 50 characters.", e.message)
//        }
//        assertTrue(exceptionThrown)
//    }

}
