package com.solutionplus.altasherat.features.signup.data.repository.remote



import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.signup.data.model.dto.SignupDto
import com.solutionplus.altasherat.features.signup.data.model.request.Phone
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
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
1. login with valid phoneRequest returns expected result
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

    @Test
    fun `when signup and get server error`() = runBlocking {
        // Arrange
        val exception = LeonException.Server.InternalServerError(404, "internal server error")
        val phone = Phone(countryCode = "0020", number = "100100100")
        val signupRequest = SignupRequest(phone = phone, password = "password")

        provider.shouldThrowException = true
        provider.exceptionToThrow = exception

        // Act & Assert
        try {
            remoteDataSource.signupWithPhone(signupRequest)
            fail("Expected an exception to be thrown")
        } catch (e: LeonException.Server.InternalServerError) {
            assertEquals(exception.message, e.message)
        }
    }


}