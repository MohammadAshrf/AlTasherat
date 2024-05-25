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
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.domain.model.Login
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

//    @Test
//    fun `when login is successful_given phone country code and password then user details are returned`() = runBlocking {
//        // Arrange
//        val phone = Phone(
//            countryCode = "0020",
//            number = "100100100"
//        )
//        val signupRequest = SignupRequest(phone = phone, password = "123456789", countryCode = "0020", countryId = 1, email = "mahmoud@gmail", firstName = "mahmoud", lastName = "Abdo", passwordConfirmation ="123456789")
//        val userInfo = User(
//            id = 1,
//            userName = "jdoe",
//            fullName = "John Doe",
//            email = "jdoe@example.com",
//            phone = phone.number
//        )
//        val accessToken = "token123"
//        val signup = Signup(message = "Success", token = "token123", user = userInfo)
//
//        coEvery { repository.signupWithPhone(signupRequest) } returns signup
//        coEvery { repository.saveUser(userInfo) } just Runs
//        coEvery { repository.saveAccessToken(accessToken) } just Runs
//        coEvery { repository.getUser() } returns domainToEntity(userInfo)
//
//        // Act
//        val result = signupUC.execute(signupRequest)
//
//        // Assert
//        assertEquals(userInfo, result)
//        assertNotNull(result)
//        coVerify {
//            repository.signupWithPhone(signupRequest)
//            repository.saveUser(userInfo)
//            repository.saveAccessToken(accessToken)
//            repository.getUser()
//        }
//    }
}
