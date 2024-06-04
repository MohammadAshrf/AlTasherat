package com.solutionplus.altasherat.common.data.repository.remote

import com.google.gson.Gson
import com.solutionplus.altasherat.android.helpers.extentions.getFileMediaAsPart
import com.solutionplus.altasherat.android.helpers.extentions.getFileMediaListAsPart
import com.solutionplus.altasherat.android.helpers.extentions.toRequestBody
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
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
        return Gson().fromJson(response.string(), responseWrappedModel) as ResponseBody
    }

    override suspend fun <ResponseBody> postWithFiles(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: Map<String, Any>?,
        filesMap: Map<String, List<File>>?
    ): ResponseBody {
        val list = mutableListOf<MultipartBody.Part>().apply {
            if (filesMap.isNullOrEmpty())
                return@apply

            filesMap.forEach {
                if (it.value.count() == 1)
                    add(it.value.first().getFileMediaAsPart(it.key))
                else
                    addAll(it.value.getFileMediaListAsPart(it.key))
            }
        }
        val bodyMap = hashMapOf<String, RequestBody>().apply {
            requestBody?.forEach { (key, body) ->
                put(key, body.toString().toRequestBody())
            }
        }

        val response = apiServices.postWithFiles(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), bodyMap = bodyMap, files = list
        )
        return Gson().fromJson(response.string(), responseWrappedModel) as ResponseBody
    }

    override suspend fun <ResponseBody> delete(
        responseWrappedModel: Type,
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): ResponseBody {
        val response = apiServices.delete(
            pathUrl = pathUrl, headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
        return Gson().fromJson(response.string(), responseWrappedModel) as ResponseBody
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
        return Gson().fromJson(response.string(), responseWrappedModel) as ResponseBody
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
        return Gson().fromJson(response.string(), responseWrappedModel) as ResponseBody
    }

    companion object {
        val logger = getClassLogger()
    }
}