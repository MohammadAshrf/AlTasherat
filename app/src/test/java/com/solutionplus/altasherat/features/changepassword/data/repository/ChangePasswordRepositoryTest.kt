package com.solutionplus.altasherat.features.changepassword.data.repository

import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.local.IChangePasswordLocalDS
import com.solutionplus.altasherat.features.changepassword.domain.repository.remote.IChangePasswordRemoteDS
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ChangePasswordRepositoryTest{
    private lateinit var repository: ChangePasswordRepository
    private val remoteDataSource: IChangePasswordRemoteDS = mockk()
    private val localDataSource: IChangePasswordLocalDS = mockk()

    @Before
    fun setUp() {
        repository = ChangePasswordRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `when changePassword is called, expect remote data source's called changePassword `() = runTest {
        // Arrange
        val request = ChangePasswordRequest(
            oldPassword = "old_password",
            newPassword = "new_password",
            newPasswordConfirmation = "new_password",
            token = "token"
        )
        coEvery { remoteDataSource.changePassword(request) } returns request

        // Act
        val result = repository.changePassword(request)

        // Assert
        coVerify(exactly = 1) { remoteDataSource.changePassword(request) }
        assertEquals(request, result)
    }

    @Test
    fun `when getAccessToken is called, expect local data source's called getAccessToken `() = runTest {
        // Arrange
        val expectedToken = "expected_token"
        coEvery { localDataSource.getAccessToken() } returns expectedToken

        // Act
        val token = repository.getAccessToKen()

        // Assert
        coVerify(exactly = 1) { localDataSource.getAccessToken() }
        assertEquals(expectedToken, token)
    }
}