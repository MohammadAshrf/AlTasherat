package com.solutionplus.altasherat.features.signup.domain.repository.local

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity

internal interface ISignupLocalDS {
//    suspend fun saveUser(userEntity: UserEntity)
    suspend fun saveAccessToken(token: String)
//    suspend fun getUser(): UserEntity

}