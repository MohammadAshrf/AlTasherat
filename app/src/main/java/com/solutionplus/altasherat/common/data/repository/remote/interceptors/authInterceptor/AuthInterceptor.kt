package com.solutionplus.altasherat.common.data.repository.remote.interceptors.authInterceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authTokenProvider: AuthTokenProvider
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Skip requests that don't need authentication
        if (originalRequest.header("No-Authentication") != null) {
            return chain.proceed(originalRequest)
        }

        val authToken = runBlocking { authTokenProvider.getAuthToken() }
        val builder = originalRequest.newBuilder()

        authToken?.let {
            builder.header("Authorization", "Bearer $it")
        }

        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}