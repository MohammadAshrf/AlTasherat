package com.solutionplus.altasherat.features.signup.data.repository.remote



import com.solutionplus.altasherat.features.signup.data.model.dto.SignupDto
import com.solutionplus.altasherat.features.signup.data.model.request.Phone
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
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
    private lateinit var remoteDataSource: SignupRemoteDS

    @Before
    fun setUp() {
        provider = FakeRestApiNetworkProvider()
        remoteDataSource = SignupRemoteDS(provider)
    }

    @Test
    fun `when Signup given valid request expect expected result`() = runTest {
        val signupRequest = SignupRequest(phone = Phone("0020","1066791541"), password = "123456789", countryCode = "0020", countryId = 1, email = "mahmoud@gmail", firstName = "mahmoud", lastName = "Abdo", passwordConfirmation ="123456789")
        val expectedResponse = SignupDto("hi","testToken", null)

        provider.postResponse = expectedResponse

        val result = remoteDataSource.signupWithPhone(signupRequest)

        assertEquals(expectedResponse, result)
    }



}