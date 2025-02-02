package com.solutionplus.altasherat.common.di

import com.solutionplus.altasherat.BuildConfig
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.constants.Constants
import com.solutionplus.altasherat.common.data.repository.remote.AlTasheratApiServices
import com.solutionplus.altasherat.common.data.repository.remote.interceptors.authInterceptor.AuthInterceptor
import com.solutionplus.altasherat.common.data.repository.remote.interceptors.authInterceptor.AuthTokenProvider
import com.solutionplus.altasherat.common.data.repository.remote.interceptors.authInterceptor.DataStoreAuthTokenProvider
import com.solutionplus.altasherat.common.data.repository.remote.RetrofitNetworkProvider
import com.solutionplus.altasherat.common.data.repository.remote.interceptors.contentTypeInterceptor.ContentTypeInterceptor
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofitNetwork(altasheratApiService: AlTasheratApiServices): INetworkProvider {
        return RetrofitNetworkProvider(altasheratApiService)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient.Builder,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideAlTasheratApiService(retrofit: Retrofit): AlTasheratApiServices =
        retrofit.create(AlTasheratApiServices::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor

    ): OkHttpClient.Builder {
        return OkHttpClient().newBuilder().apply {
            connectTimeout(30L, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            connectionPool(
                ConnectionPool(30L.toInt(), 500000, TimeUnit.MILLISECONDS)
            )
            readTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)
            addInterceptor(ContentTypeInterceptor())
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(authInterceptor)
        }
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor { message ->
            if (BuildConfig.DEBUG) logger.warning(message)
        }.apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }


    @Provides
    @Singleton
    fun provideDataStoreAuthTokenProvider(
         storageKV: IKeyValueStorageProvider,
         encryptionProvider: IEncryptionProvider
    ): AuthTokenProvider {
        return DataStoreAuthTokenProvider(storageKV, encryptionProvider)
    }

    private val logger = getClassLogger()
}