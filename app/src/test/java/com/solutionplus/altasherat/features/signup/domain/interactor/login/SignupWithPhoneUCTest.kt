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
import com.solutionplus.altasherat.features.signup.data.mapper.UserMapper
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
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        repository = mockk()
        signupUC = SignupUC(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test successful signup with phone, country code, and password`() = runTest {
        val phone = Phone(countryCode = "0020", number = "100100100")
        val signupRequest = SignupRequest(phone = phone, password = "123456789", countryCode = "0020", countryId = 1, email = "mahmoud@gmail", firstName = "mahmoud", lastName = "Abdo", passwordConfirmation ="123456789")

        val user = User(
            id = 1,
            userName = "jdoe",
            fullName = "John Doe",
            email = "jdoe@example.com",
            phone = phone.number
        )

        val signup = Signup(message = "Success", token = "token123", user = user)

        coEvery { repository.signupWithPhone(signupRequest) } returns signup
        coEvery { repository.saveUser(user) } just Runs
        coEvery { repository.saveAccessToken(signup.token) } just Runs
        coEvery { repository.getUser() } returns UserMapper.domainToEntity(user)

        val result = signupUC.execute(signupRequest)
        advanceUntilIdle()

        assertNotNull(result)
        println("result: $result")
        println("result class: ${result?.javaClass?.simpleName}")
        assertTrue(result is User)
        assertEquals(user, result)

        coVerify { repository.signupWithPhone(signupRequest) }
        coVerify { repository.saveUser(user) }
        coVerify { repository.saveAccessToken(signup.token) }
        coVerify { repository.getUser() }
    }

//    @Test
//    fun `test login failure due to unauthorized exception`() = runTest {
//        val phone = Phone(countryCode = "0020", number = "100100100")
//        val loginRequest = LoginRequest(phone = phone, password = "wrongpassword")
//
//        val exception = HttpException(Response.error<Any>(401, ResponseBody.create(null, "")))
//        coEvery { repository.loginWithPhone(loginRequest) } throws exception
//
//        var result: Resource<User>? = null
//        loginWithPhoneUC(this, loginRequest) {
//            result = it
//        }
//
//        advanceUntilIdle()
//
//        assertNotNull(result)
//        println("result: $result")
//        println("result class: ${result?.javaClass?.simpleName}")
//        assertTrue(result is Resource.Failure)
//        assertEquals(LeonException.Client.Unauthorized, (result as Resource.Failure).exception)
//
//        coVerify { repository.loginWithPhone(loginRequest) }
//        coVerify(exactly = 0) { repository.saveUser(any()) }
//        coVerify(exactly = 0) { repository.saveAccessToken(any()) }
//        coVerify(exactly = 0) { repository.getUser() }
//    }
//
//    @Test
//    fun `test login failure due to empty password`() = runTest {
//        // Given a login request with an empty password
//        val phone = Phone(countryCode = "0020", number = "100100100")
//        val loginRequest = LoginRequest(phone = phone, password = "")
//
//        // When invoking the use case
//        var result: Resource<User>? = null
//        loginWithPhoneUC(this, loginRequest) {
//            result = it
//        }
//
//        // Then the result should be a failure
//        assertTrue(result is Resource.Failure)
//        assertEquals(LeonException.Client.Unauthorized, (result as Resource.Failure).exception)
//    }
//
//    @Test
//    fun `test login failure due to invalid phone number`() = runTest {
//        // Given a login request with an invalid phone number
//        val phone = Phone(countryCode = "0020", number = "invalid")
//        val loginRequest = LoginRequest(phone = phone, password = "123456789")
//
//        // When invoking the use case
//        var result: Resource<User>? = null
//        loginWithPhoneUC(this, loginRequest) {
//            result = it
//        }
//
//        // Then the result should be a failure
//        assertTrue(result is Resource.Failure)
//        assertEquals(LeonException.Client.Unauthorized, (result as Resource.Failure).exception)
//    }
//
//    @Test
//    fun `test login failure due to network error`() = runTest {
//        // Given a valid login request
//        val phone = Phone(countryCode = "0020", number = "100100100")
//        val loginRequest = LoginRequest(phone = phone, password = "123456789")
//
//        // Mock the repository behavior to throw an IOException
//        val exception = IOException("Network Error")
//        coEvery { repository.loginWithPhone(loginRequest) } throws exception
//
//        // When invoking the use case
//        var result: Resource<User>? = null
//        loginWithPhoneUC(this, loginRequest) {
//            result = it
//        }
//
//        // Then the result should be a failure with the expected exception
//        assertTrue(result is Resource.Failure)
//        assertEquals(LeonException.Network.Unhandled(R.string.network_unavailable, "Network Error"), (result as Resource.Failure).exception)
//    }

}
