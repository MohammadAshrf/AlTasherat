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
    @Before
    fun setUp() {
        repository = mockk()
        loginWithPhoneUC = LoginWithPhoneUC(repository)
    }


    @Test
    fun `when login is successful_given phone country code and password then user details are returned`() = runBlocking {
        // Arrange
        val phone = Phone(countryCode = "0020", number = "100100100")
        val loginRequest = LoginRequest(phone = phone, password = "password")
        val userInfo = User(
            id = 1,
            userName = "jdoe",
            fullName = "John Doe",
            email = "jdoe@example.com",
            phone = phone.number
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

}
