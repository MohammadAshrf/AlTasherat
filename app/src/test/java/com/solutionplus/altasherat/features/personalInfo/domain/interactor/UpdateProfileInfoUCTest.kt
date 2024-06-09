package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.features.personalInfo.data.models.request.PhoneRequest
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateProfileInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import org.junit.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import org.junit.jupiter.api.assertThrows
import java.io.File

@ExperimentalCoroutinesApi
class UpdateProfileInfoUCTest{
    private lateinit var repository: IUpdateProfileRepository
    private lateinit var saveUserUC: SaveUserUC
    private lateinit var updateProfileInfoUC: UpdateProfileInfoUC

    @Before
    fun setUp() {
        repository = mockk()
        saveUserUC = mockk()
        updateProfileInfoUC = UpdateProfileInfoUC(repository, saveUserUC)
    }

    @Test
    fun `when first name is invalid then throw validation exception`() = runBlocking {
        // Arrange
        val request = UpdateProfileInfoRequest(
            firstname = "Jo",
            middleName = "Middle",
            lastname = "Doe",
            email = "johndoe@example.com",
            phone = PhoneRequest("1", "1234567890"),
            image = null,
            birthdate = "2000-01-01",
            country = 1
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            updateProfileInfoUC.execute(request)
        }

        // Assert
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.FIRST_NAME))
    }

    @Test
    fun `when middle name is invalid then throw validation exception`() = runBlocking {
        // Arrange
        val request = UpdateProfileInfoRequest(
            firstname = "John",
            middleName = "ThisIsATooLongMiddleName",
            lastname = "Doe",
            email = "johndoe@example.com",
            phone = PhoneRequest("1", "1234567890"),
            image = null,
            birthdate = "2000-01-01",
            country = 1
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            updateProfileInfoUC.execute(request)
        }

        // Assert
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.MIDDLE_NAME))

    }

    @Test
    fun `when last name is invalid then throw validation exception`() = runBlocking {
        // Arrange
        val request = UpdateProfileInfoRequest(
            firstname = "John",
            middleName = "Middle",
            lastname = "Do",
            email = "johndoe@example.com",
            phone = PhoneRequest("1", "1234567890"),
            image = null,
            birthdate = "2000-01-01",
            country = 1
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            updateProfileInfoUC.execute(request)
        }

        // Assert
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.LAST_NAME))

    }

    @Test
    fun `when email is invalid then throw validation exception`() = runBlocking {
        // Arrange
        val request = UpdateProfileInfoRequest(
            firstname = "John",
            middleName = "Middle",
            lastname = "Doe",
            email = "invalid-email",
            phone = PhoneRequest("1", "1234567890"),
            image = null,
            birthdate = "2000-01-01",
            country = 1
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            updateProfileInfoUC.execute(request)
        }

        // Assert
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.EMAIL))

    }

    @Test
    fun `when phone is invalid then throw validation exception`() = runBlocking {
        // Arrange
        val request = UpdateProfileInfoRequest(
            firstname = "John",
            middleName = "Middle",
            lastname = "Doe",
            email = "johndoe@example.com",
            phone = PhoneRequest("1", "invalid-phone"),
            image = null,
            birthdate = "2000-01-01",
            country = 1
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            updateProfileInfoUC.execute(request)
        }

        // Assert
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.PHONE))

    }

    @Test
    fun `when country is invalid then throw validation exception`() = runBlocking {
        // Arrange
        val request = UpdateProfileInfoRequest(
            firstname = "John",
            middleName = "Middle",
            lastname = "Doe",
            email = "johndoe@example.com",
            phone = PhoneRequest("1", "1234567890"),
            image = null,
            birthdate = "2000-01-01",
            country = 0
        )

        // Act & Assert
        val exception = assertThrows<LeonException.Local.RequestValidation> {
            updateProfileInfoUC.execute(request)
        }

        // Assert
        assertNotNull(exception)
        assertTrue(exception.errors.containsKey(Validation.COUNTRY))

    }
}