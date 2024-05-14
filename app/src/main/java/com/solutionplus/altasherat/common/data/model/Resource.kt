package com.solutionplus.altasherat.common.data.model

import com.solutionplus.altasherat.common.data.model.exception.LeonException


sealed class Resource<model>() {
    data class Success<Model>(val data: Model) : Resource<Model>()
    data class Loading<Model>(val loading: Boolean = true) : Resource<Model>()
    data class Error<Model>(val exception: LeonException) : Resource<Model>()
}