package com.solutionplus.altasherat.common.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {
    @Provides
    @Singleton

    fun provideWorkManga(@ApplicationContext context: Context, factory: HiltWorkerFactory): WorkManager {

        val configuration = Configuration.Builder().setWorkerFactory(factory).build()
        WorkManager.initialize(context, configuration)

        return WorkManager.getInstance(context)
    }
}