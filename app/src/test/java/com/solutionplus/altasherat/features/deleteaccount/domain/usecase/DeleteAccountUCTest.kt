package com.solutionplus.altasherat.features.deleteaccount.domain.usecase

import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.Resource
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.IDeleteAccountRepository
import com.solutionplus.altasherat.features.signup.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import org.junit.Assert.*
import io.mockk.*
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

/*
   when password request has invalid password then throw validation exception
   when password request has empty password then throw validation exception
   when password request has max length character greater than 50 in password then throw validation exception
   test when request is null then no repository methods are called
 */
class DeleteAccountUCTest{
    private lateinit var repository: IDeleteAccountRepository
    private lateinit var deleteAccountUC: DeleteAccountUC

    @Before
    fun setUp() {
        repository = mockk()
        deleteAccountUC = DeleteAccountUC(repository)
    }

    @Test
    fun `when password request has invalid password then throw validation exception`() = runBlocking {
        // Arrange
        val request = DeleteAccountRequest(password ="short")
        //Act
        val expected = deleteAccountUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when password request has empty password then throw validation exception`() = runBlocking {
        // Arrange
        val request = DeleteAccountRequest(password ="")
        //Act
        val expected = deleteAccountUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun `when password request has max length character greater than 50 in password then throw validation exception`() = runBlocking {
        // Arrange
        val request = DeleteAccountRequest(password ="12".repeat(50))
        //Act
        val expected = deleteAccountUC(request).drop(1).first()
        //Assert
        assertTrue((expected as Resource.Failure).exception is LeonException.Local.RequestValidation)
    }

    @Test
    fun ` when request is null then no repository methods are called `() = runBlocking {
        // Act
        deleteAccountUC(null)

        // Assert
        coVerify(exactly = 0) { repository.deleteAccount(any()) }
    }

}