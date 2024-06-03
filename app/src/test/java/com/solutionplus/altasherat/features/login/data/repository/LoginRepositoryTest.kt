package com.solutionplus.altasherat.features.login.data.repository


import com.solutionplus.altasherat.features.login.data.mapper.UserMapper
import com.solutionplus.altasherat.features.login.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.PhoneRequest
import com.solutionplus.altasherat.features.login.domain.model.Phone
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.solutionplus.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
    fun `when saving user given valid user then user saved`() = runBlocking {
        // Arrange
        val phoneRequest = Phone(countryCode = "0020", number = "100100100")
        val user = User(
            id = 1,
            username = "userName",
            email = "email",
            firstname = "firstName",
            middleName = "middleName",
            lastname = "lastName",
            phone = phoneRequest,
            birthdate = null,
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
        val phoneRequest = Phone(countryCode = "0020", number = "100100100")
        val user = User(
            id = 1,
            username = "testUser",
            firstname = "John",
            middleName = "Doe",
            lastname = "Smith",
            email = "testUser@example.com",
            phone = phoneRequest,
            birthdate = "1990-01-01",
            emailVerified = true
        )


        coEvery { localDs.getUser() } returns UserMapper.domainToEntity(user)

        val result = repository.getUser()

        coVerify { localDs.getUser() }
        assertEquals(user, result)
    }


}

