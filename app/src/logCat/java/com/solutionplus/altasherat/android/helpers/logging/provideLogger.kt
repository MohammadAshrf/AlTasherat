package com.solutionplus.altasherat.android.helpers.logging

import com.solutionplus.altasherat.android.helpers.logging.writers.LogcatWriter
import com.intuit.sdp.BuildConfig

object LoggerProvider {
    fun provideLogger(tagKey: String = "SolutionX-Full") {
        LoggerFactory.setLogWriter(LogcatWriter(tagKey, BuildConfig.DEBUG))
    }
}