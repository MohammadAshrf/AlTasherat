package com.solutionplus.altasherat.android.helpers.logging

import am.leon.solutionx.android.helpers.logging.LoggerFactory
import am.leon.solutionx.android.helpers.logging.writers.DummyWriter
import am.leon.solutionx.android.helpers.logging.writers.FileWriter
import am.leon.solutionx.android.helpers.logging.writers.LogcatWriter
import com.intuit.sdp.BuildConfig


object LoggerProvider {
    fun provideLogger() {
        LoggerFactory.setLogWriter(DummyWriter())
    }
}