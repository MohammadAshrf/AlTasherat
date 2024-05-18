package com.solutionplus.altasherat.common.presentation.ui.base.delegation

import com.solutionplus.altasherat.common.data.model.exception.LeonException

interface ErrorHandling {
    fun handleHttpExceptions(exception: LeonException )
}