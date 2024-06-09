package com.solutionplus.altasherat.features.deleteaccount.domain.usecase

import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.IDeleteAccountRepository
import org.junit.Assert.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

/*
   test validation when password is empty
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
    fun `when password is empty then throw the validation error `() = runBlocking {
        // Arrange
        val request = DeleteAccountRequest(password = "")

        // Act
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            deleteAccountUC.execute(request)
        }

        // Assert
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.PASSWORD))

        // Verify that repository methods are not called
        coVerify(exactly = 0) { repository.deleteAccount(any()) }
        coVerify(exactly = 0) { repository.deleteUser() }
        coVerify(exactly = 0) { repository.deleteAccessToken() }
    }

    @Test
    fun ` when request is null then no repository methods are called `() = runBlocking {
        // Act
        deleteAccountUC.execute(null)

        // Assert
        coVerify(exactly = 0) { repository.deleteAccount(any()) }
    }
}