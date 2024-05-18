package com.solutionplus.altasherat.features.login.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import java.lang.reflect.Type

class FakeRestApiNetworkProvider : INetworkProvider {

        var postResponse: Any? = null

    override suspend fun <ResponseBody> get(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParameters: Map<String, Any>?
    ): ResponseBody {
        throw UnsupportedOperationException("Not implemented")
    }

    override suspend fun <ResponseBody, RequestBody> put(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParameters: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        throw UnsupportedOperationException("Not implemented")
    }

    override suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        @Suppress("UNCHECKED_CAST")
        return postResponse as ResponseBody
    }

    override suspend fun <ResponseBody, RequestBody> delete(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?
    ): ResponseBody {
        throw UnsupportedOperationException("Not implemented")
    }

}