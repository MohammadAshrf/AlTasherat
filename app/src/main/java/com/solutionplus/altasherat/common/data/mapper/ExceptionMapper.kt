package com.solutionplus.altasherat.common.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionMapper {
    fun map(exception: Throwable): LeonException {
        return when (exception) {
            is HttpException -> {
                when (val code = exception.code()) {
                    401 -> LeonException.Client.Unauthorized
                    422 -> {
                        val errorBody = exception.response()?.errorBody()?.string()
                        val type = object : TypeToken<Map<String, Any>>() {}.type
                        val errorMap: Map<String, Any> = Gson().fromJson(errorBody, type)
                        val errors = errorMap["errors"] as? Map<String, String> ?: emptyMap()
                        val message = errorMap["message"] as? String ?: "Unknown validation error"
                        LeonException.Client.ResponseValidation(errors, message)
                    }
                    in 400..499 -> LeonException.Client.Unhandled(code, exception.message())
                    in 500..599 -> LeonException.Server.InternalServerError(
                        code,
                        exception.message()
                    )

                    else -> LeonException.Unknown("HTTP Error: $code")
                }
            }

            is IOException -> {
                when (exception) {
                    is UnknownHostException ->
                        LeonException.Network.Unhandled(
                            R.string.network_unavailable,
                            message = "Network Error: ${exception.message}"
                        )

                    is SocketTimeoutException ->
                        LeonException.Network.Retrial(
                            R.string.timeout_error,
                            message = "Network Error: ${exception.message}"
                        )

                    else ->
                        LeonException.Network.Unhandled(
                            R.string.unsupported_type,
                            message = "Network Error: ${exception.message}"
                        )
                }
            }

            else -> LeonException.Unknown("An unknown error occurred: ${exception.message}")
        }
    }
}