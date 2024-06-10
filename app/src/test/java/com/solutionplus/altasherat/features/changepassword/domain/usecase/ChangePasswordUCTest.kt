package com.solutionplus.altasherat.features.changepassword.domain.usecase

import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.IchangePasswordRepository
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.assertThrows

/*
-- when old password invalid then throw validation exception
-- when old password is empty then throw validation exception
-- when old password has max length character greater than 50 then throw validation exception
-- when new password invalid then throw validation exception
-- when new password is empty then throw validation exception
-- when new password has max length character greater than 50 then throw validation exception
-- when new password confirmation invalid then throw validation exception
-- when new password confirmation is empty then throw validation exception
-- when new password  confirmation has max length character greater than 50 then throw validation exception
-- when new password and confirmation do not match
 */

class ChangePasswordUCTest {

    private lateinit var changePasswordUC: ChangePasswordUC
    private val repository: IchangePasswordRepository = mockk()

    @Before
    fun setUp() {
        changePasswordUC = ChangePasswordUC(repository)
    }

    //-------------------------validation-----------------------------------------//
    @Test
    fun `when old password invalid then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "123",
            newPassword = "123456789",
            newPasswordConfirmation = "12345678910"
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when old password is empty then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "",
            newPassword = "123456789",
            newPasswordConfirmation = "12345678910"
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when old password has max length character greater than 50 then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "12".repeat(50),
            newPassword = "123456789",
            newPasswordConfirmation = "12345678910"
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when new password invalid then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "123456789",
            newPassword = "123",
            newPasswordConfirmation = "12345678910"
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when new password is empty then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "123456789",
            newPassword = "",
            newPasswordConfirmation = "12345678910"
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when new password has max length character greater than 50 then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "123456789",
            newPassword = "12".repeat(50),
            newPasswordConfirmation = "12345678910"
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }


    @Test
    fun `when new password confirmation invalid then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "123456789",
            newPassword = "12345678910",
            newPasswordConfirmation = "123"
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when new password confirmation is empty then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "123456789",
            newPassword = "12345678910",
            newPasswordConfirmation = ""
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when new password confirmation has max length character greater than 50 then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "123456789",
            newPassword = "12345678910",
            newPasswordConfirmation = "12".repeat(50)
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when new password and confirmation do not match then throw validation exception`() = runBlocking {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "validOldPassword123",
            newPassword = "123456789",
            newPasswordConfirmation = "12345678910"
        )
        //Act
        val expected = changePasswordUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

}