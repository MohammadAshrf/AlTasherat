package com.solutionplus.altasherat.common.data.model

import com.solutionplus.altasherat.common.data.model.exception.LeonException

sealed class Resource<out Model> {
    data class Loading<Model>(val loading: Boolean, val partialData: Model? = null) :
        Resource<Model>()

    data class Success<out Model>(val model: Model) : Resource<Model>()
    data class Failure(val exception: LeonException) : Resource<Nothing>()

    companion object {
        fun <Model> loading(
            loading: Boolean = true, partialData: Model? = null
        ): Resource<Model> = Loading(loading, partialData)

        fun <Model> success(model: Model): Resource<Model> = Success(model)
        fun <Model> failure(exception: LeonException): Resource<Model> = Failure(exception)
    }
}