package com.solutionplus.altasherat.android.helpers.logging

import am.leon.solutionx.android.helpers.logging.LoggerFactory
import am.leon.solutionx.android.helpers.logging.writers.FileWriter
import am.leon.solutionx.android.helpers.logging.writers.LogcatWriter
import com.intuit.sdp.BuildConfig


object LoggerProvider {
    fun provideLogger(tagKey: String = "SolutionX-Full") {
        LoggerFactory.setLogWriter(
            FileWriter(
                folderName = tagKey,
                fileName = "SolutionX-Full-logFile",
                tagKey = tagKey,
                isDebugEnabled = BuildConfig.DEBUG
            )
        )
    }
}