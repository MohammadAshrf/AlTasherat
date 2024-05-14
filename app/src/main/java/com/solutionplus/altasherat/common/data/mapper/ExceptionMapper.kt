package com.solutionplus.altasherat.common.data.mapper

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.R
import retrofit2.HttpException
import java.io.IOException

class ExceptionMapper {
    fun map(exception: Throwable): LeonException {
        return when (exception) {
            is HttpException -> {
                val code = exception.code()
                when (code) {
                    in 400..499 -> LeonException.Client.Unauthorized
                    in 500..599 -> LeonException.Server.InternalServerError(code, exception.message())
                    else -> LeonException.Unknown("HTTP Error: $code")
                }
            }
            is IOException -> LeonException.Network.Unhandled(R.string.unsupported_type,message = "Network Error: ${exception.message}")
            else -> LeonException.Unknown("Unknown error occurred: ${exception.message}")
        }
    }
}