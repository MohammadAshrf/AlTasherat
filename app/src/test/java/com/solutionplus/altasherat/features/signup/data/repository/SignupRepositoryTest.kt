package com.solutionplus.altasherat.features.signup.data.repository



import com.solutionplus.altasherat.features.signup.data.mapper.SignupMapper
import com.solutionplus.altasherat.features.signup.data.mapper.UserMapper
import com.solutionplus.altasherat.features.signup.data.model.dto.PhoneDto
import com.solutionplus.altasherat.features.signup.data.model.dto.SignupDto
import com.solutionplus.altasherat.features.signup.data.model.dto.UserDto
import com.solutionplus.altasherat.features.signup.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.signup.data.model.request.Phone
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.model.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import com.solutionplus.altasherat.features.signup.domain.repository.local.ISignupLocalDS
import com.solutionplus.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS
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


class SignupRepositoryTest {
    private lateinit var remoteDs: ISignupRemoteDS
    private lateinit var localDs: ISignupLocalDS
    private lateinit var repository: SignupRepository

    @Before
    fun setUp() {
        remoteDs = mock(ISignupRemoteDS::class.java)
        localDs = mock(ISignupLocalDS::class.java)
        repository = SignupRepository(remoteDs, localDs)
    }

    @Test
    fun testLoginWithPhone() {
        runBlocking {
            val phoneNumber = Phone("0020", "100100100")
            val signupRequest = SignupRequest(phone = phoneNumber, password = "123456789", countryCode = "0020", countryId = 1, email = "mahmoud@gmail", firstName = "mahmoud", lastName = "Abdo", passwordConfirmation ="123456789")
            val user = UserDto(
                id = 1,
                username = "jdoe",
                firstname = "John",
                lastname = "Doe",
                email = "jdoe@example.com",
                phone = PhoneDto("0020", "123456789"),
                birthdate = "1990-01-01"
            )
            val loginResponse = SignupDto("", "mnsnln2356", user)

            // Mock remote data source behavior
            whenever(remoteDs.signupWithPhone(signupRequest)).thenReturn(loginResponse)

            // Call the method to be tested
            val result = repository.signupWithPhone(signupRequest)

            // Verify interactions and assertions
            verify(remoteDs).signupWithPhone(signupRequest)
            assertEquals(SignupMapper.dtoToDomain(loginResponse), result)
        }
    }

    @Test
    fun testSaveUser() {
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
    fun `test saving user with different data`() = runBlocking {
        val user = User(id = 1, userName = "user1", email = "user1@example.com", phone = "123456789")
        val userEntity = UserMapper.domainToEntity(user)
        val localDs = mockk<ISignupLocalDS>()
        val repository = SignupRepository(mockk(), localDs)

        coEvery { localDs.saveUser(userEntity) } just Runs

        // Act
        repository.saveUser(user)

        // Assert
        // Verify that saveUser method of local data source is called with the correct user entity
        coVerify { localDs.saveUser(userEntity) }
    }

    @Test
    fun `test saving user is null`() = runBlocking {
        val user = User(id = null, userName = null, email = null, phone = null)
        val userEntity = UserMapper.domainToEntity(user)
        val localDs = mockk<ISignupLocalDS>()
        val repository = SignupRepository(mockk(), localDs)

        coEvery { localDs.saveUser(userEntity) } just Runs

        // Act
        repository.saveUser(user)

        // Assert
        // Verify that saveUser method of local data source is called with the correct user entity
        coVerify { localDs.saveUser(userEntity) }
    }

    @Test
    fun `test saving user with invalid data`() {
        runBlocking {
            // Arrange
            val user = User(id = 1) // Missing required fields like username, email, etc.
            val localDataSource = mockk<ISignupLocalDS>()
            val repository: ISignupRepository = SignupRepository(mockk(), localDataSource)

            // Act & Assert
            assertThrows<Exception> {
                repository.saveUser(user)
            }
        }
    }

    @Test
    fun testSaveAccessToken() = runBlocking{
        val token = "sampleToken"

        // Call the method to be tested
        repository.saveAccessToken(token)

        // Verify interactions
        verify(localDs).saveAccessToken(token)
    }

    @Test
    fun testGetUser() = runBlocking{
        val userEntity = UserEntity()

        // Mock local data source behavior
        whenever(localDs.getUser()).thenReturn(userEntity)

        val result = repository.getUser()

        // Verify interactions and assertions
        verify(localDs).getUser()
        assertEquals(userEntity, result)
    }

}

