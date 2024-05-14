package com.solutionplus.altasherat.android

import android.app.Application
<<<<<<< HEAD
import com.solutionplus.altasherat.android.helpers.logging.LoggerProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp : Application() {
=======
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.intuit.sdp.BuildConfig
import com.solutionplus.altasherat.android.helpers.logging.LoggerProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class BaseApp : Application(), Configuration.Provider {


    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) Log.DEBUG else Log.ERROR)
            .setWorkerFactory(workerFactory)
            .build()


>>>>>>> cfb1dac1eda7749c5779221ceab1a6dc04d7939a
    override fun onCreate() {
        super.onCreate()
        LoggerProvider.provideLogger()
    }
}