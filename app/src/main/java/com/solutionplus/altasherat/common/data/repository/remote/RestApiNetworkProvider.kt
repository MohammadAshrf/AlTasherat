package com.solutionplus.altasherat.common.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

internal class RestApiNetworkProvider  @Inject constructor(private val serviceApi: ServiceApi):
    IRestApiNetworkProvider {
    override suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody
    ): ResponseBody {
        val response  = serviceApi.post(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), requestBody = requestBody ?: Unit
        )

        return Gson().fromJson(response.string(), responseWrappedModel)
    }

    override suspend fun <ResponseBody, RequestBody> delete(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        val response = serviceApi.delete(
            pathUrl = pathUrl,
            headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )

        return Gson().fromJson(response.string(), responseWrappedModel)

    }

    override suspend fun <ResponseBody, RequestBody> put(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        val response = serviceApi.put(
            pathUrl = pathUrl,
            headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(),
            requestBody = requestBody ?: Unit
        )

        return Gson().fromJson(response.string(), responseWrappedModel)

    }

    override suspend fun <ResponseBody> get(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): ResponseBody {
        val response = serviceApi.get(
            pathUrl = pathUrl,
            headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
            return Gson().fromJson(response.string(), responseWrappedModel)
    }


}