package com.solutionplus.altasherat.features.services.user.data.repository

import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.domain.models.Image
import com.solutionplus.altasherat.features.services.user.domain.models.Phone
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.services.user.domain.repository.Local.IUserLocalDS
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserRepositoryTest{
    private lateinit var localDs: IUserLocalDS
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        localDs = mockk()
        userRepository = UserRepository(localDs)
    }

        @Test
    fun ` when saving user given valid user expect user saved `() = runBlocking {
        // Arrange
        val phoneRequest = Phone(countryCode = "0020", number = "100100100", extension = "", id = -1, type = "", holderName = "")
        val image = Image(id = 1, type = "profile", path = "http://example.com/image.jpg", title = "Profile Image", updatedAt = "2023-01-01", description = "User profile picture", createdAt = "2023-01-01", main = true, priority = 1)
        val country = Country(id = 1, name = "Egypt", code = "EG", flag = "🇪🇬", currency = "EGP", phoneCode = "+20")
        val user = User(id = 1, username = "userName", email = "email", firstname = "firstName", middleName = "middleName", lastname = "lastName", phone = phoneRequest, image = image, birthdate = "1990-01-01", emailVerified = true, phoneVerified = true, blocked = 0, country = country, allPermissions = listOf("READ", "WRITE"))
        val userEntity = UserMapper.domainToEntity(user)
        coEvery { localDs.saveUser(userEntity) } returns Unit

        // Act
        userRepository.saveUser(user)

        // Assert
        coVerify { localDs.saveUser(userEntity) }
    }

//    @Test
//    fun `when getting user then return user entity `() = runBlocking {
//        val phoneRequest = PhoneEntity(countryCode = "0020", number = "100100100", extension = "", id = -1, type = "", holderName = "")
//        val image = ImageEntity(id = 1, type = "profile", path = "http://example.com/image.jpg", title = "Profile Image", updatedAt = "2023-01-01", description = "User profile picture", createdAt = "2023-01-01", main = true, priority = 1)
//        val country = CountryEntity(id = 1, name = "Egypt", code = "EG", flag = "🇪🇬", currency = "EGP", phoneCode = "+20")
//        val userEntity = UserEntity(id = 1, username = "userName", email = "email", firstname = "firstName", middleName = "middleName", lastname = "lastName", phone = phoneRequest, image = image, birthdate = "1990-01-01", emailVerified = true, phoneVerified = true, blocked = 0, country = country, allPermissions = listOf("READ", "WRITE"))
//        val user = UserMapper.entityToDomain(userEntity)
//
//        coEvery { localDs.getUser() } returns  userEntity
//
//        // Act
//        val result = userRepository.getUser()
//
//        // Assert
//        coVerify { localDs.getUser() }
//        assertEquals(user, result)
//    }
}