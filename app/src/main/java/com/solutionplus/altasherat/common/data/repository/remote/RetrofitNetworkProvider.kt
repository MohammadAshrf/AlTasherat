package com.solutionplus.altasherat.common.data.repository.remote

import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import java.lang.reflect.Type

class RetrofitNetworkProvider(private val apiServices: AlTasheratApiServices) : INetworkProvider {
    override suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        val response = apiServices.post(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), requestBody = requestBody ?: Unit
        )
        return Gson().fromJson(response.body()?.string(), responseWrappedModel) as ResponseBody
    }

    override suspend fun <ResponseBody, RequestBody> delete(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        val response = apiServices.delete(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), requestBody = requestBody ?: Unit
        )
        return Gson().fromJson(response.body()?.string(), responseWrappedModel) as ResponseBody
    }

    override suspend fun <ResponseBody, RequestBody> put(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        val response = apiServices.put(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), requestBody = requestBody ?: Unit
        )
        return Gson().fromJson(response.body()?.string(), responseWrappedModel) as ResponseBody
    }

    override suspend fun <ResponseBody> get(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): ResponseBody {
        val response = apiServices.get(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
        return Gson().fromJson(response.body()?.string(), responseWrappedModel) as ResponseBody
    }

    companion object {
        val logger = getClassLogger()
    }
}