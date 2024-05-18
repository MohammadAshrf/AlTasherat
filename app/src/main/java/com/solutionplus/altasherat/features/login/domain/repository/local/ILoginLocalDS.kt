package com.solutionplus.altasherat.features.login.domain.repository.local

import com.solutionplus.altasherat.features.login.data.model.entity.UserEntity

internal interface ILoginLocalDS {
    suspend fun saveUser(userEntity: UserEntity)
    suspend fun saveAccessToken(token: String)
    suspend fun getUser(): UserEntity

}