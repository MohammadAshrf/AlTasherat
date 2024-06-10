package com.solutionplus.altasherat.android.helpers.logging

import com.solutionplus.altasherat.android.helpers.logging.LoggerFactory
import com.solutionplus.altasherat.android.helpers.logging.writers.DummyWriter
import com.solutionplus.altasherat.android.helpers.logging.writers.FileWriter
import com.solutionplus.altasherat.android.helpers.logging.writers.LogcatWriter
import com.solutionplus.altasherat.BuildConfig


object LoggerProvider {
    fun provideLogger() {
        LoggerFactory.setLogWriter(DummyWriter())
    }
}