package com.solutionplus.altasherat.android.helpers.logging

import com.solutionplus.altasherat.android.helpers.logging.LoggerFactory
import com.solutionplus.altasherat.android.helpers.logging.writers.FileWriter
import com.solutionplus.altasherat.android.helpers.logging.writers.LogcatWriter
import com.solutionplus.altasherat.BuildConfig


object LoggerProvider {
    fun provideLogger(tagKey: String = "Altasherat") {
        LoggerFactory.setLogWriter(
            FileWriter(
                folderName = tagKey,
                fileName = "Altasherat-logFile",
                tagKey = tagKey,
                isDebugEnabled = BuildConfig.DEBUG
            )
        )
    }
}