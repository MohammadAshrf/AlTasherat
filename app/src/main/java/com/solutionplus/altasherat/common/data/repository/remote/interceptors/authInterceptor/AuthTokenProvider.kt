package com.solutionplus.altasherat.common.data.repository.remote.interceptors.authInterceptor

interface AuthTokenProvider {
    suspend fun getAuthToken(): String?
}

