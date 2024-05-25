package com.solutionplus.altasherat.features.changepassword.data.repository

import com.solutionplus.altasherat.features.changepassword.data.model.dto.ChangePasswordDto
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.local.IChangePasswordLocalDS
import com.solutionplus.altasherat.features.changepassword.domain.repository.remote.IChangePasswordRemoteDS
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.runBlocking
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.*
import org.junit.Assert.*
import org.mockito.kotlin.capture

/*
test the newpassword is equal newPasswordConfirmation
test when token is null then return no access token
test check the change password request could be null
test the validation new password and old password and newPasswordConfirmation < 8 (at least 8 characters)
 */

class ChangePasswordRepositoryTest{
//    private lateinit var remoteDs: IChangePasswordRemoteDS
//    private lateinit var localDs: IChangePasswordLocalDS
//    private lateinit var changePasswordRepository: ChangePasswordRepository
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//        remoteDs = mock(IChangePasswordRemoteDS::class.java)
//        localDs = mock(IChangePasswordLocalDS::class.java)
//        changePasswordRepository = ChangePasswordRepository(remoteDs, localDs)
//    }
//
//    @Test
//    fun `validate oldPassword with less than 8 characters`() {
//        val request = ChangePasswordRequest(oldPassword = "1234567")
//        assertFalse(request.validateOldPassword())
//    }
//
//    @Test
//    fun `validate oldPassword with exactly 8 characters`() {
//        val request = ChangePasswordRequest(oldPassword = "12345678")
//        assertTrue(request.validateOldPassword())
//    }
//
//    @Test
//    fun `validate oldPassword with more than 50 characters`() {
//        val request = ChangePasswordRequest(oldPassword = "a".repeat(51))
//        assertFalse(request.validateOldPassword())
//    }
//
//    @Test
//    fun `validate newPassword with less than 8 characters`() {
//        val request = ChangePasswordRequest(newPassword = "1234567")
//        assertFalse(request.validateNewPassword())
//    }
//
//    @Test
//    fun `validate newPassword with exactly 8 characters`() {
//        val request = ChangePasswordRequest(newPassword = "12345678")
//        assertTrue(request.validateNewPassword())
//    }
//
//    @Test
//    fun `validate newPassword with more than 50 characters`() {
//        val request = ChangePasswordRequest(newPassword = "a".repeat(51))
//        assertFalse(request.validateNewPassword())
//    }
//
//    @Test
//    fun `validate newPasswordConfirmation with less than 8 characters`() {
//        val request = ChangePasswordRequest(newPasswordConfirmation = "1234567")
//        assertFalse(request.validateNewPasswordConfirmation())
//    }
//
//    @Test
//    fun `validate newPasswordConfirmation with exactly 8 characters`() {
//        val request = ChangePasswordRequest(newPasswordConfirmation = "12345678")
//        assertTrue(request.validateNewPasswordConfirmation())
//    }
//
//    @Test
//    fun `validate newPasswordConfirmation with more than 50 characters`() {
//        val request = ChangePasswordRequest(newPasswordConfirmation = "a".repeat(51))
//        assertFalse(request.validateNewPasswordConfirmation())
//    }
//
//    @Test
//    fun `test Validate NewPassword Equal NewPasswordConfirmation`() {
//        val request = ChangePasswordRequest(newPassword = "newPassword123", newPasswordConfirmation = "newPassword123")
//        assertTrue(request.validateNewPasswordEqualNewPasswordConfirmation())
//
//        val invalidRequest = ChangePasswordRequest(newPassword = "123456789", newPasswordConfirmation = "12345678910")
//        assertFalse(invalidRequest.validateNewPasswordEqualNewPasswordConfirmation())
//    }
//

}