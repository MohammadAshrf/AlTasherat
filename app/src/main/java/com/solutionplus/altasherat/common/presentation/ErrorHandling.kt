package com.solutionplus.altasherat.common.presentation

import com.solutionplus.altasherat.common.data.model.exception.LeonException

interface ErrorHandling {
    fun handleHttpExceptions(exception: LeonException )
}