package com.solutionplus.altasherat.features.changepassword.domain.usecase

import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.IchangePasswordRepository
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

/*
-- Verify that changePassword is called with valid parameters.
-- Verify that changePassword is not called when parameters are null.
-- Verify that the token method returns the expected token.
-- Verify that the token method returns null if the repository returns null.
 */
@ExperimentalCoroutinesApi
class ChangePasswordUCTest {

    private lateinit var changePasswordUC: ChangePasswordUC
    private val repository: IchangePasswordRepository = mockk()

    @Before
    fun setUp() {
        changePasswordUC = ChangePasswordUC(repository)
    }

    @Test
    fun `when execute is called with valid parameters, expect call repository's changePassword`() = runTest {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "old_password",
            newPassword = "new_password",
            newPasswordConfirmation = "new_password",
            token = "token"
        )
        coEvery { repository.changePassword(request) } returns request

        // Act
        changePasswordUC.execute(request)

        // Assert
        coVerify(exactly = 1) { repository.changePassword(request) }
    }

    @Test
    fun `when params are null, expected execute should not call changePassword `() = runTest {
        // Act
        changePasswordUC.execute(null)

        // Assert
        coVerify(exactly = 0) { repository.changePassword(any()) }
    }

    @Test
    fun `when token is called and repository returns a non-null token, expect repository's returned token`() = runTest {
        // Arrange
        val expectedToken = "expected_token"
        coEvery { repository.getAccessToKen() } returns expectedToken

        // Act
        val token = changePasswordUC.token()

        // Assert
        assertEquals(expectedToken, token)
        coVerify(exactly = 1) { repository.getAccessToKen() }
    }

    @Test
    fun `when token is called and repository returns null, expect returned null`() = runTest {
        // Arrange
        coEvery { repository.getAccessToKen() } returns null

        // Act
        val token = changePasswordUC.token()

        // Assert
        assertNull(token)
        coVerify(exactly = 1) { repository.getAccessToKen() }
    }

}