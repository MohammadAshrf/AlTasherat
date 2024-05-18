package com.solutionplus.altasherat.features.login.data.repository.remote


import com.solutionplus.altasherat.features.login.data.model.dto.LoginDto
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.Phone
import com.solutionplus.altasherat.features.signup.data.repository.remote.FakeRestApiNetworkProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

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
    fun loginWithPhoneReturnsExpectedResult() = runTest {
        val loginRequest = LoginRequest(Phone("002", "100100100"), "123456789")
        val expectedResponse = LoginDto("hi","testToken", null)

        provider.postResponse = expectedResponse

        val result = remoteDataSource.loginWithPhone(loginRequest)

        assertEquals(expectedResponse, result)
    }


    @Test
    fun loginWithPhoneReturnsNullWhenProviderReturnsNull() = runTest {
        val loginRequest = LoginRequest(Phone("002", "100100100"), "testCode")
        val expectedResponse = null

        provider.postResponse = expectedResponse

        val result = remoteDataSource.loginWithPhone(loginRequest)

        assertEquals(expectedResponse, result)
        assertNull(result)
    }


}