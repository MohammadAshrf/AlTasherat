package com.solutionplus.altasherat.features.signup.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import java.io.File
import java.lang.reflect.Type

class FakeRestApiNetworkProvider : INetworkProvider {

        var postResponse: Any? = null
    var shouldThrowException: Boolean = false
    var exceptionToThrow: Exception? = null

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
        if (shouldThrowException && exceptionToThrow != null) {
            throw exceptionToThrow as Throwable
        }
        @Suppress("UNCHECKED_CAST")
        return postResponse as ResponseBody
    }

    override suspend fun <ResponseBody> postWithFiles(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: Map<String, Any>?,
        filesMap: Map<String, List<File>>?
    ): ResponseBody {
        return postResponse as ResponseBody
    }

    override suspend fun <ResponseBody> delete(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): ResponseBody {
        return postResponse as ResponseBody
    }


}