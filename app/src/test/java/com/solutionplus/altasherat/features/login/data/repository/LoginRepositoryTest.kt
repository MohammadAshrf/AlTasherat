package com.solutionplus.altasherat.features.login.data.repository


import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.features.login.data.mapper.LoginMapper
import com.solutionplus.altasherat.features.login.data.mapper.UserMapper
import com.solutionplus.altasherat.features.login.data.model.dto.LoginDto
import com.solutionplus.altasherat.features.login.data.model.dto.PhoneDto
import com.solutionplus.altasherat.features.login.data.model.dto.UserDto
import com.solutionplus.altasherat.features.login.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.Phone
import com.solutionplus.altasherat.features.login.domain.model.Login
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.solutionplus.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.assertThrows
import java.util.Base64


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
    fun `when saving user given valid user then user saved`() = runBlocking {
        // Arrange
        val user = User(
            id = 1,
            userName = "userName",
            fullName = "fullName",
            email = "email",
            firstName = "firstName",
            middleName = "middleName",
            lastName = "lastName",
            phone = "phone",
            birthDate = null,
            imageUrl = "imageUrl",
            emailVerified = true
        )

        val userEntity = UserMapper.domainToEntity(user)

        coEvery { localDs.saveUser(userEntity) } returns Unit

        // Act
        repository.saveUser(user)

        // Assert
        coVerify { localDs.saveUser(userEntity) }
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

    @Test
    fun `when getting user then return user entity `() = runBlocking {
        val userEntity = UserEntity(
        id = 1,
        userName = "testUser",
        firsName = "John",
        middleName = "Doe",
        lastName = "Smith",
        fullName = "John Doe Smith",
        email = "testUser@example.com",
        phone = "1234567890",
        birthDate = "1990-01-01",
        imageUrl = "http://example.com/image.jpg",
        emailVerified = true
    )

        coEvery { localDs.getUser() } returns userEntity

        val result = repository.getUser()

        coVerify { localDs.getUser() }
        assertEquals(userEntity, result)
    }


}

