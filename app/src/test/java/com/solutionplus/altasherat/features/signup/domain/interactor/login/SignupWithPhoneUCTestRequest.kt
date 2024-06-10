package com.solutionplus.altasherat.features.signup.domain.interactor.login

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.Resource
import io.mockk.*
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserFromLocalUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import com.solutionplus.altasherat.features.signup.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.services.user.domain.models.Image
import com.solutionplus.altasherat.features.services.user.domain.models.Phone
import com.solutionplus.altasherat.features.signup.domain.model.Signup
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import com.solutionplus.altasherat.features.signup.domain.usecase.SignupUC
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import org.junit.jupiter.api.assertThrows

/*
- when signup is successful_ then user details are returned
- when signup request has invalid first name then throw validation exception
- when signup request has max length character greater than 15 in first name then throw validation exception
- when signup request has empty first name then throw validation exception
- when signup request has invalid last name then throw validation exception
- when signup request has max length character greater than 15 in last name then throw validation exception
- when signup request has empty last name then throw validation exception
- when signup request has max length character greater than 50 in email then throw validation exception
- when signup request has empty email then throw validation exception
- when signup request has empty phone number then throw validation exception
- when signup request has invalid phone number then throw validation exception
- when signup request  has max length character greater than 15 in phone number then throw validation exception
- when signup request has max length character greater than 50 in password then throw validation exception
- when signup request has empty password then throw validation exception
- when signup request has invalid password then throw validation exception
 */
 class SignupWithPhoneUCTestRequest {

    private lateinit var repository: ISignupRepository
    private lateinit var saveUserUC: SaveUserUC
    private lateinit var getUserUC: GetUserFromLocalUC
    private lateinit var signupUC: SignupUC

    @Before
    fun setUp() {
        repository = mockk()
        saveUserUC = mockk()
        getUserUC = mockk()
        signupUC = SignupUC(repository, saveUserUC, getUserUC)
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
        coEvery { saveUserUC.execute(userInfo) } just Runs
        coEvery { repository.saveAccessToken(accessToken) } just Runs
        coEvery { getUserUC.execute(Unit) } returns userInfo

        // Act
        val result= signupUC(signupRequest)
        var resultUser: User? = null
        var thrownException: LeonException.Server.InternalServerError? = null
        result.collect { resource ->
            when (resource) {
                is Resource.Loading -> { }
                is Resource.Success -> resultUser = resource.model
                is Resource.Failure -> thrownException = resource.exception as? LeonException.Server.InternalServerError
            }
        }

        // Assert
        assertEquals(userInfo, resultUser)
        coVerify {
            repository.signupWithPhone(signupRequest)
            saveUserUC.execute(userInfo)
            repository.saveAccessToken(accessToken)
            getUserUC.execute(Unit)
        }
    }

    //------------------------------Validation------------------------------------//
    @Test
    fun `when signup request has invalid first name then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(firstName = "Jo", lastName = "Doe", email = "john.doe@example.com", phone = PhoneRequest(countryCode = "0020", number = "100100100"), password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has max length character greater than 15 in first name then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(firstName = "john".repeat(15), lastName = "Doe", email = "john.doe@example.com", phone = PhoneRequest(countryCode = "0020", number = "100100100"), password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has empty first name then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(firstName = "", lastName = "Doe", email = "john.doe@example.com", phone = PhoneRequest(countryCode = "0020", number = "100100100"), password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has invalid last name then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(firstName = "John", lastName = "mm", email = "john.doe@example.com", phone = PhoneRequest(countryCode = "0020", number = "100100100"), password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has max length character greater than 15 in last name then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(firstName = "John", lastName = "mm".repeat(15), email = "john.doe@example.com", phone = PhoneRequest(countryCode = "0020", number = "100100100"), password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has empty last name then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(firstName = "John", lastName = "", email = "john.doe@example.com", phone = PhoneRequest(countryCode = "0020", number = "100100100"), password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }
    @Test
    fun `when signup request has max length character greater than 50 in email then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(
            firstName = "John", lastName = "Doe", email = "${"aac".repeat(50)}@gmail.com", phone = PhoneRequest(countryCode = "0020", number = "100100100"), password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20"
        )
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has empty email then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(
            firstName = "John", lastName = "Doe", email = "", phone = PhoneRequest(countryCode = "0020", number = "100100100"), password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20"
        )
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has empty phone number then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "", number = "")
        val signupRequest = SignupRequest(firstName = "John", lastName = "Doe", email = "john.doe@example.com", phone = phoneRequest, password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has invalid phone number then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "123")
        val signupRequest = SignupRequest(firstName = "John", lastName = "Doe", email = "john.doe@example.com", phone = phoneRequest, password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request  has max length character greater than 15 in phone number then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "13".repeat(15))
        val signupRequest = SignupRequest(firstName = "John", lastName = "Doe", email = "john.doe@example.com", phone = phoneRequest, password = "password", passwordConfirmation = "password", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has invalid password then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(firstName = "John", lastName = "Doe", email = "john.doe@example.com", phone = PhoneRequest(countryCode = "0020", number = "100100100")
            , password = "short", passwordConfirmation = "short", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has empty password then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(firstName = "John", lastName = "Doe", email = "john.doe@example.com", phone = PhoneRequest(countryCode = "0020", number = "100100100")
            , password = "", passwordConfirmation = "short", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when signup request has max length character greater than 50 in password then throw validation exception`() = runBlocking {
        // Arrange
        val signupRequest = SignupRequest(firstName = "John", lastName = "Doe", email = "john.doe@example.com", phone = PhoneRequest(countryCode = "0020", number = "100100100")
            , password = "123".repeat(50), passwordConfirmation = "short", countryId = 1, countryCode = "+20")
        //Act
        val expected = signupUC(signupRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }
}
