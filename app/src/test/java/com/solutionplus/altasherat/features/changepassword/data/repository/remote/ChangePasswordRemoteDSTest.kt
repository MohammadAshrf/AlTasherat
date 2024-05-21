package com.solutionplus.altasherat.features.changepassword.data.repository.remote

import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/*
-- Verify that changePassword is successful, expect returned ChangePasswordRequest
 */

@ExperimentalCoroutinesApi
class ChangePasswordRemoteDSTest {
    private lateinit var provider: FakeRestApiNetworkProvider
    private lateinit var remoteDataSource: ChangePasswordRemoteDS

    @Before
    fun setUp() {
        provider = FakeRestApiNetworkProvider()
        remoteDataSource = ChangePasswordRemoteDS(provider)
    }

    @Test
    fun `when changePassword is successful, expect returned ChangePasswordRequest`() = runTest {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "old_password",
            newPassword = "new_password",
            newPasswordConfirmation = "new_password",
            token = "token"
        )
        val expectedResponse = request
        provider.postResponse = expectedResponse

        // Act
        val result = remoteDataSource.changePassword(request)

        // Assert
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `when changePassword fails, expect returned null `() = runTest {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "old_password",
            newPassword = "new_password",
            newPasswordConfirmation = "new_password",
            token = "token"
        )
        provider.postResponse = null

        // Act
        val result = remoteDataSource.changePassword(request)

        // Assert
        assertNull(result)
    }

}
