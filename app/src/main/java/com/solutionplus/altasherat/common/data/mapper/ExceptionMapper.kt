package com.solutionplus.altasherat.common.data.mapper

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionMapper {
    fun map(exception: Throwable): LeonException {
        return when (exception) {
            is HttpException -> {
                val code = exception.code()
                when (code) {
                    401 -> LeonException.Client.Unauthorized
                    422 -> LeonException.Client.ResponseValidation(emptyMap(), exception.message())
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