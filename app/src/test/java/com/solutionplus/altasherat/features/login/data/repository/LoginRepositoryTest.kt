package com.solutionplus.altasherat.features.login.data.repository


import com.solutionplus.altasherat.features.login.data.mapper.LoginMapper
import com.solutionplus.altasherat.features.login.data.mapper.UserMapper
import com.solutionplus.altasherat.features.login.data.model.dto.LoginDto
import com.solutionplus.altasherat.features.login.data.model.dto.PhoneDto
import com.solutionplus.altasherat.features.login.data.model.dto.UserDto
import com.solutionplus.altasherat.features.login.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.login.data.model.request.LoginRequest
import com.solutionplus.altasherat.features.login.data.model.request.Phone
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.solutionplus.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.assertThrows


class LoginRepositoryTest {
    private lateinit var remoteDs: ILoginRemoteDS
    private lateinit var localDs: ILoginLocalDS
    private lateinit var repository: LoginRepository

    @Before
    fun setUp() {
        remoteDs = mock(ILoginRemoteDS::class.java)
        localDs = mock(ILoginLocalDS::class.java)
        repository = LoginRepository(remoteDs, localDs)
    }

    @Test
    fun `when login with phone given valid request expect successful login response`() {
        runBlocking {
            val phoneNumber = Phone("0020", "100100100")
            val loginRequest = LoginRequest(phone = phoneNumber, password = "123456789")
            val user = UserDto(
                id = 1,
                username = "jdoe",
                firstname = "John",
                lastname = "Doe",
                email = "jdoe@example.com",
                phone = PhoneDto("0020", "123456789"),
                birthdate = "1990-01-01"
            )
            val loginResponse = LoginDto("", "mnsnln2356", user)

            // Mock remote data source behavior
            whenever(remoteDs.loginWithPhone(loginRequest)).thenReturn(loginResponse)

            // Call the method to be tested
            val result = repository.loginWithPhone(loginRequest)

            // Verify interactions and assertions
            verify(remoteDs).loginWithPhone(loginRequest)
            assertEquals(LoginMapper.dtoToDomain(loginResponse), result)
        }
    }

    @Test
    fun `when saving user given valid user expect user saved`() {
        runBlocking {
            val user = User()
            val userEntity = UserMapper.domainToEntity(user)

            // Call the method to be tested
            repository.saveUser(user)

            // Verify interactions
            verify(localDs).saveUser(userEntity)
        }
    }


    @Test
    fun `when saving user given different data expect user saved with correct data`() = runBlocking {
        val user = User(id = 1, userName = "user1", email = "user1@example.com", phone = "123456789")
        val userEntity = UserMapper.domainToEntity(user)
        val localDs = mockk<ILoginLocalDS>()
        val repository = LoginRepository(mockk(), localDs)

        coEvery { localDs.saveUser(userEntity) } just Runs

        // Act
        repository.saveUser(user)

        // Assert
        // Verify that saveUser method of local data source is called with the correct user entity
        coVerify { localDs.saveUser(userEntity) }
    }

    @Test
    fun `when saving user given null fields expect user saved`() = runBlocking {
        val user = User(id = null, userName = null, email = null, phone = null)
        val userEntity = UserMapper.domainToEntity(user)
        val localDs = mockk<ILoginLocalDS>()
        val repository = LoginRepository(mockk(), localDs)

        coEvery { localDs.saveUser(userEntity) } just Runs

        // Act
        repository.saveUser(user)

        // Assert
        // Verify that saveUser method of local data source is called with the correct user entity
        coVerify { localDs.saveUser(userEntity) }
    }

    @Test
    fun `when saving user given invalid data expect exception thrown`() {
        runBlocking {
            // Arrange
            val user = User(id = 1)
            val localDataSource = mockk<ILoginLocalDS>()
            val repository: ILoginRepository = LoginRepository(mockk(), localDataSource)

            // Act & Assert
            assertThrows<Exception> {
                repository.saveUser(user)
            }
        }
    }

    @Test
    fun `when saving access token given valid token expect token saved`() = runBlocking{
        val token = "sampleToken"

        // Call the method to be tested
        repository.saveAccessToken(token)

        // Verify interactions
        verify(localDs).saveAccessToken(token)
    }

    @Test
    fun `when getting user expect correct user entity returned`() = runBlocking{
        val userEntity = UserEntity()

        // Mock local data source behavior
        whenever(localDs.getUser()).thenReturn(userEntity)

        val result = repository.getUser()

        // Verify interactions and assertions
        verify(localDs).getUser()
        assertEquals(userEntity, result)
    }

}

