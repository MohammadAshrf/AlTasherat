package com.solutionplus.altasherat.features.login.domain.interactor.login

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
import com.google.common.truth.Truth.assertThat
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.mapper.UserMapper
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.Phone
import com.solutionplus.altasherat.features.login.domain.interactor.login.LoginWithPhoneUC
import com.solutionplus.altasherat.features.login.domain.model.Login
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/*
- validation
 */
class LoginWithPhoneUCTest {

    private lateinit var repository: ILoginRepository
    private lateinit var loginWithPhoneUC: LoginWithPhoneUC
    private val testDispatcher = UnconfinedTestDispatcher()
    @Before
    fun setUp() {
        repository = mockk()
        loginWithPhoneUC = LoginWithPhoneUC(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test successful login with phone, country code , and password `() = runTest {
        val phone = Phone(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phone = phone, password = "123456789")

        val user = User(
            id = 1,
            userName = "jdoe",
            fullName = "John Doe",
            email = "jdoe@example.com",
            phone = phone.number
        )

        val login = Login(message = "Success", accessToken = "token123", userInfo = user)

        coEvery { repository.loginWithPhone(loginRequest) } returns login
        coEvery { repository.saveUser(user) } just Runs
        coEvery { repository.saveAccessToken(login.accessToken) } just Runs
        coEvery { repository.getUser() } returns UserMapper.domainToEntity(user)

        var result: Resource<User>? = null
        loginWithPhoneUC(CoroutineScope(Dispatchers.Default), loginRequest) {
            result = it
        }

        advanceUntilIdle()

        assertNotNull(result)
        println("result: $result")
        println("result class: ${result?.javaClass?.simpleName}")
        assertTrue(result is Resource.Success<User>)
        assertEquals(user, (result as Resource.Success<User>).model)

        coVerify { repository.loginWithPhone(loginRequest) }
        coVerify { repository.saveUser(user) }
        coVerify { repository.saveAccessToken(login.accessToken) }
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
