package com.solutionplus.altasherat.common.domain.repository.remote

import java.io.File
import java.lang.reflect.Type

interface INetworkProvider {
    suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null, requestBody: RequestBody? = null
    ): ResponseBody

    suspend fun <ResponseBody> postWithFiles(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null, requestBody: Map<String, Any>?,
        filesMap: Map<String, List<File>>?
    ): ResponseBody

    suspend fun <ResponseBody> delete(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null
    ): ResponseBody

    suspend fun <ResponseBody, RequestBody> put(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null, requestBody: RequestBody? = null
    ): ResponseBody

    suspend fun <ResponseBody> get(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null
    ): ResponseBody
}