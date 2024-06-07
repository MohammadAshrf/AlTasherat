package com.solutionplus.altasherat.features.signup.domain.repository

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.model.Signup
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface ISignupRepository {
    suspend fun signupWithPhone(signupRequest: SignupRequest): Signup
//    suspend fun getCountries(): List<Country>
//    suspend fun saveUser(user: User)
    suspend fun saveAccessToken(token: String)
//    suspend fun getUser(): UserEntity?
}