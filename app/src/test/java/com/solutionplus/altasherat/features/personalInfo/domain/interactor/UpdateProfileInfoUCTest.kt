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
}