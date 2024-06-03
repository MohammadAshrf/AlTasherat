package com.solutionplus.altasherat.features.signup.data.repository


import com.solutionplus.altasherat.features.signup.data.mapper.UserMapper
import com.solutionplus.altasherat.features.signup.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.signup.domain.model.User
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
    fun `when saving user given valid user expect user saved`() = runBlocking {
        // Arrange
        val user = User()
        val userEntity = UserMapper.domainToEntity(user)
        coEvery { localDs.saveUser(userEntity) } returns Unit

        // Act
        repository.saveUser(user)

        // Assert
        coVerify { localDs.saveUser(userEntity) }
    }

    @Test
    fun `when saving user given null fields then user saved`() = runBlocking {
        //Arrange
        val user = User(id = null, username = null, email = null, phone = null)
        val userEntity = UserMapper.domainToEntity(user)
        coEvery { localDs.saveUser(userEntity) } returns Unit
        // Act
        repository.saveUser(user)

        // Assert
        coVerify {localDs.saveUser(userEntity)}
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

    @Test
    fun `when getting user then return user entity `() = runBlocking {
        val userEntity = User()

        coEvery { localDs.getUser() } returns  UserMapper.domainToEntity(userEntity)

        val result = repository.getUser()


        coVerify { localDs.getUser() }
        assertEquals(userEntity, result)
    }

}

