package com.solutionplus.altasherat.features.changepassword.domain.usecase

import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.IchangePasswordRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

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
    fun `when token is called and repository , then repository's returned token`() = runTest {
        // Arrange
        val expectedToken = "expected_token"
        coEvery { repository.getAccessToKen() } returns expectedToken

        // Act
        val token = changePasswordUC.token()

        // Assert
        assertEquals(expectedToken, token)
    }

    @Test
    fun `when token is called and repository returns null, expect returned null`() = runTest {
        // Arrange
        coEvery { repository.getAccessToKen() } returns null

        // Act
        val token = changePasswordUC.token()

        // Assert
        assertNull(token)
    }


    //-------------------------validation-----------------------------------------//
//    @Test
//    fun `test invalid old password`() = runBlocking {
//        // Arrange
//        val request = ChangePasswordRequest(
//            oldPassword = "12345",
//        )
//
//        coEvery { repository.changePassword(request) } returns request
//
//        // Act & Assert
//        val exception = assertThrows<LeonException.Local.RequestValidation> {
//            changePasswordUC.execute(request)
//        }
//
//        // Verify
//        assertNotNull(exception)
//        assertTrue(exception.errors.containsKey(Validation.OLD_PASSWORD))
//    }

//    @Test
//    fun `test invalid when old password Empty`() = runBlocking {
//        // Arrange
//        val request = ChangePasswordRequest(
//            oldPassword = "",
//        )
//
//        coEvery { repository.changePassword(request) } returns request
//
//        // Act & Assert
//        val exception = assertThrows<LeonException.Local.RequestValidation> {
//            changePasswordUC.execute(request)
//        }
//
//        // Verify
//        assertNotNull(exception)
//        assertTrue(exception.errors.containsKey(Validation.OLD_PASSWORD))
//    }

    @Test
    fun `test invalid new password`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "validOldPassword123",
            newPassword = "short",
            newPasswordConfirmation = "short"
        )

        coEvery { repository.changePassword(request) } returns request

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            changePasswordUC.execute(request)
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.NEW_PASSWORD))
    }

    @Test
    fun `test invalid when new password Empty`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "validOldPassword123",
            newPassword = "",
            newPasswordConfirmation = "short"
        )

        coEvery { repository.changePassword(request) } returns request

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            changePasswordUC.execute(request)
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.NEW_PASSWORD))
    }

    @Test
    fun `test new password and confirmation do not match`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "validOldPassword123",
            newPassword = "123456789",
            newPasswordConfirmation = "12345678910"
        )

        coEvery { repository.changePassword(request) } returns request

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            changePasswordUC.execute(request)
        }

        // Verify
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.NEW_PASSWORD_EQUAL_CONFIRMATION))
    }
}