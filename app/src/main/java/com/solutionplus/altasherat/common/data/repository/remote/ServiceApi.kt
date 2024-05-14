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

    @GET("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun get(
        @Path("pathUrl") pathUrl: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>
    ): ResponseBody

    @POST("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun post(
        @Path("pathUrl") pathUrl: String, @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>, @Body requestBody: Any
    ): ResponseBody

    @DELETE("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun delete(
        @Path("pathUrl") pathUrl: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>
    ): ResponseBody

    @PUT("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun put(
        @Path("pathUrl") pathUrl: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>,
        @Body requestBody: Any
    ): ResponseBody

}