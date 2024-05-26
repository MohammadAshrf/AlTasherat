package com.solutionplus.altasherat.features.login.data.repository.remote


import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.model.dto.LoginDto
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.Phone
import com.solutionplus.altasherat.features.signup.data.repository.remote.FakeRestApiNetworkProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.fail

//import kotlin.test.assertFailsWith


/*
test cases
1. login with valid phone returns expected result
2. test login with network error
3. loginWithPhoneReturnsNullWhenProviderReturnsNull
* */
class RemoteDataSourceTest {

    private lateinit var provider: FakeRestApiNetworkProvider
    private lateinit var remoteDataSource: LoginRemoteDS

    @Before
    fun setUp() {
        provider = FakeRestApiNetworkProvider()
        remoteDataSource = LoginRemoteDS(provider)
    }

    @Test
    fun `when login with phone given valid request then expected result`() = runTest {
        val loginRequest = LoginRequest(Phone("002", "100100100"), "123456789")
        val expectedResponse = LoginDto("hi","testToken", null)

        provider.postResponse = expectedResponse

        val result = remoteDataSource.loginWithPhone(loginRequest)

        assertEquals(expectedResponse, result)
    }

//    @Test
//    fun `when login and get server error`() = runBlocking {
//        // Arrange
//        val exception = LeonException.Server.InternalServerError(404, "internal server error")
//        val phone = Phone(countryCode = "0020", number = "100100100")
//        val loginRequest = LoginRequest(phone = phone, password = "password")
//
//        provider.shouldThrowException = true
//        provider.exceptionToThrow = exception
//
//        // Act & Assert
//        try {
//            remoteDataSource.loginWithPhone(loginRequest)
//            fail("Expected an exception to be thrown")
//        } catch (e: LeonException.Server.InternalServerError) {
//            assertEquals(exception.message, e.message)
//        }
//    }


}