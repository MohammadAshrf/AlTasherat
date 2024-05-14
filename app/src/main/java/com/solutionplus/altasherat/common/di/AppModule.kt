package com.solutionplus.altasherat.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
<<<<<<< HEAD

=======
>>>>>>> cfb1dac1eda7749c5779221ceab1a6dc04d7939a
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
}