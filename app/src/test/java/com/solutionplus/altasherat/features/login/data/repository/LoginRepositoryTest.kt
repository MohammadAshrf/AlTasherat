package com.solutionplus.altasherat.features.login.data.repository


import com.solutionplus.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.solutionplus.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class LoginRepositoryTest {
    private lateinit var remoteDs: ILoginRemoteDS
    private lateinit var localDs: ILoginLocalDS
    private lateinit var repository: LoginRepository

    @Before
    fun setUp() {
        remoteDs = mockk()
        localDs = mockk()
        repository = LoginRepository(remoteDs, localDs)
    }

    @Test
    fun `when saving access token given valid token expect token saved`() = runBlocking {
        val token = "sampleToken"

        coEvery { localDs.saveAccessToken(token) } returns Unit

        // Act
        repository.saveAccessToken(token)

        // Assert
        coVerify { localDs.saveAccessToken(token) }
    }

    @Test
    fun `when saving access token with empty then token saved`() = runBlocking {
        val token = ""

        coEvery { localDs.saveAccessToken(token) } returns Unit

        // Act
        repository.saveAccessToken(token)

        // Assert
        coVerify { localDs.saveAccessToken(token) }
    }
}

