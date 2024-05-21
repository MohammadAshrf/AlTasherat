package com.solutionplus.altasherat.features.signup.domain.repository

import com.solutionplus.altasherat.features.signup.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.model.Signup
import com.solutionplus.altasherat.features.signup.domain.model.User

interface ISignupRepository {
    suspend fun signupWithPhone(signupRequest: SignupRequest): Signup
//    suspend fun getCountries(): List<Country>
    suspend fun saveUser(user: User)
    suspend fun saveAccessToken(token: String)
    suspend fun getUser(): UserEntity?
}