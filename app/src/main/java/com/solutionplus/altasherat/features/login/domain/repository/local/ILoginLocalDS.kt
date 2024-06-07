package com.solutionplus.altasherat.features.login.domain.repository.local


internal interface ILoginLocalDS {
//    suspend fun saveUser(userEntity: UserEntity)
    suspend fun saveAccessToken(token: String)
//    suspend fun getUser(): UserEntity

}