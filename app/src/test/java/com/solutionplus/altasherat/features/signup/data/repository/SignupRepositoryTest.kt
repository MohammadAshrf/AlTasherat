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
    fun `when saving user given valid user expect user saved`() = runBlocking {
        val user = User()
        val userEntity = UserMapper.domainToEntity(user)


        repository.saveUser(user)


        verify(localDs).saveUser(userEntity)
    }

    @Test
    fun `when saving user given null fields then user saved`() = runBlocking {
        //Arrange
        val user = User(id = null, userName = null, email = null, phone = null)
        val userEntity = UserMapper.domainToEntity(user)

        // Act
        repository.saveUser(user)

        // Assert
        verify(localDs).saveUser(userEntity)
    }



    @Test
    fun `when saving access token given valid token then token saved`() = runBlocking {
       //Arrange
        val token = "sampleToken"

        //Act
        repository.saveAccessToken(token)

        // Assert
        verify(localDs).saveAccessToken(token)
    }

    @Test
    fun `when getting user then return user entity `() = runBlocking {
        val userEntity = UserEntity()

        whenever(localDs.getUser()).thenReturn(userEntity)

        val result = repository.getUser()


        verify(localDs).getUser()
        assertEquals(userEntity, result)
    }

}

