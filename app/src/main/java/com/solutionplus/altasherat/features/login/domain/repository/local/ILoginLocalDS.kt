package com.solutionplus.altasherat.features.login.domain.repository.local


internal interface ILoginLocalDS {
    suspend fun saveAccessToken(token: String)

}