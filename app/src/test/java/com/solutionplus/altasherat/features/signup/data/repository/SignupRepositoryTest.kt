package com.solutionplus.altasherat.features.signup.data.repository


import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.domain.models.Image
import com.solutionplus.altasherat.features.services.user.domain.models.Phone
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.signup.domain.repository.local.ISignupLocalDS
import com.solutionplus.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class SignupRepositoryTest {
    private lateinit var remoteDs: ISignupRemoteDS
    private lateinit var localDs: ISignupLocalDS
    private lateinit var repository: SignupRepository

    @Before
    fun setUp() {
        remoteDs = mockk()
        localDs = mockk()
        repository = SignupRepository(remoteDs, localDs)
    }

    @Test
    fun `when saving access token given valid token then token saved`() = runBlocking {
       //Arrange
        val token = "sampleToken"
        coEvery { localDs.saveAccessToken(token) } returns Unit

        //Act
        repository.saveAccessToken(token)

        // Assert
        coVerify { localDs.saveAccessToken(token) }
    }

    @Test
    fun `when saving access token with empty string then token saved`() = runBlocking {
        //Arrange
        val token = ""
        coEvery { localDs.saveAccessToken(token) } returns Unit

        //Act
        repository.saveAccessToken(token)

        // Assert
        coVerify { localDs.saveAccessToken(token) }
    }

}

