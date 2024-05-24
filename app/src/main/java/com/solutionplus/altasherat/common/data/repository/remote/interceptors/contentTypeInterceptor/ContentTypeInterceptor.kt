package com.solutionplus.altasherat.common.data.repository.remote.interceptors.contentTypeInterceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ContentTypeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val builder = originalRequest.newBuilder()

        // Add "Accept" and "Content-Type" headers
        builder.header("Accept", "application/json")
        builder.header("Content-Type", "application/json")

        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}