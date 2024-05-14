package com.solutionplus.altasherat.common.di

import com.solutionplus.altasherat.common.domain.repository.local.keyValue.IStorageKeyValue
import android.content.Context
import com.solutionplus.altasherat.common.data.repository.local.keyValue.DataStoreStorageKeyValue
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object StorageDI {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): IStorageKeyValue =
        DataStoreStorageKeyValue(context)
}