package com.solutionplus.altasherat.common.data.repository.remote

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Url

internal interface ServiceApi {

    @GET
    @JvmSuppressWildcards
    suspend fun get(
        @Url url: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>
    ): ResponseBody

    @POST
    @JvmSuppressWildcards
    suspend fun post(
        @Url url: String, @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>, @Body requestBody: Any
    ): ResponseBody

    @DELETE
    @JvmSuppressWildcards
    suspend fun delete(
        @Url url: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>
    ): ResponseBody

    @PUT
    @JvmSuppressWildcards
    suspend fun put(
        @Url url: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>,
        @Body requestBody: Any
    ): ResponseBody

}