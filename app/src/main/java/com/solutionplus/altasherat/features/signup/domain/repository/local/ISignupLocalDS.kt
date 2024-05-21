package com.solutionplus.altasherat.features.signup.domain.repository.local

import com.solutionplus.altasherat.features.signup.data.model.entity.UserEntity

internal interface ISignupLocalDS {
    suspend fun saveUser(userEntity: UserEntity)
    suspend fun saveAccessToken(token: String)
    suspend fun getUser(): UserEntity

}