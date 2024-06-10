package com.solutionplus.altasherat.features.login.domain.interactor.login


import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.Resource
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
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

/*
- when login is successful_ then user details are returned
- when login request has empty phone number then throw validation exception
- when login request has invalid phone number then throw validation exception
- when login request  has max length character greater than 15 in phone number then throw validation exception
- when login request has max length character greater than 50 in password then throw validation exception
- when login request has empty password then throw validation exception
- when login request has invalid password then throw validation exception
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
            val result= loginWithPhoneUC(loginRequest)
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
                repository.loginWithPhone(loginRequest)
                saveUserUC.execute(userInfo)
                repository.saveAccessToken(accessToken)
                getUserUC.execute(Unit)
            }
        }

//-------------------------Validation Tests-----------------------------------------//
    @Test
    fun `when login request has empty phone number then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "123456789")
        //Act
        val expected = loginWithPhoneUC(loginRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when login request has invalid phone number then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "1020")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "123456789")
        //Act
        val expected = loginWithPhoneUC(loginRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when login request  has max length character greater than 15 in phone number then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "10".repeat(15))
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "")
        //Act
        val expected = loginWithPhoneUC(loginRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when login request has invalid password then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "12")
        //Act
        val expected = loginWithPhoneUC(loginRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when login request has empty password then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "")
        //Act
        val expected = loginWithPhoneUC(loginRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when login request has max length character greater than 50 in password then throw validation exception`() = runBlocking {
        // Arrange
        val phoneRequest = PhoneRequest(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phoneRequest = phoneRequest, password = "12".repeat(50))
        //Act
        val expected = loginWithPhoneUC(loginRequest).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }
}
