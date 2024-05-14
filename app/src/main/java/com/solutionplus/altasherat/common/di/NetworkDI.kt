package com.solutionplus.altasherat.common.di

import com.solutionplus.altasherat.common.data.repository.remote.ServiceApi
import com.solutionplus.altasherat.BuildConfig
import com.solutionplus.altasherat.common.presentation.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkDI {

    @Provides
    @Singleton
    fun getRetroBuilder(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(
                OkHttpClient.Builder().also { client ->
                    if (BuildConfig.DEBUG){
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiServices(retrofit: Retrofit): ServiceApi =
        retrofit.create(ServiceApi::class.java)
}