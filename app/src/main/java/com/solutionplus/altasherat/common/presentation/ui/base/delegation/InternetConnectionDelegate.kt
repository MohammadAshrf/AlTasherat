package com.solutionplus.altasherat.common.presentation.ui.base.delegation

import android.content.Context

interface InternetConnectionDelegate {
    fun isInternetAvailable(context: Context): Boolean
}