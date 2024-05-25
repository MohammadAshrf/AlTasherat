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
        remoteDs = mock(ILoginRemoteDS::class.java)
        localDs = mock(ILoginLocalDS::class.java)
        repository = LoginRepository(remoteDs, localDs)
    }

    @Test
    fun `when saving user given valid user then user saved`() {
        runBlocking {
            val user = User()
            val userEntity = UserMapper.domainToEntity(user)

            repository.saveUser(user)

            verify(localDs).saveUser(userEntity)
        }
    }

    @Test
    fun `when saving access token given valid token expect token saved`() = runBlocking{
        val token = "sampleToken"

        repository.saveAccessToken(token)

        verify(localDs).saveAccessToken(token)
    }

    @Test
    fun `when getting user then return user entity `() = runBlocking{
        val userEntity = UserEntity()

        whenever(localDs.getUser()).thenReturn(userEntity)

        val result = repository.getUser()

        verify(localDs).getUser()
        assertEquals(userEntity, result)
    }



}

