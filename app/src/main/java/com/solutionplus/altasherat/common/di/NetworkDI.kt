package com.solutionplus.altasherat.common.di

<<<<<<< HEAD
import com.solutionplus.altasherat.common.data.repository.remote.ServiceApi
import com.solutionplus.altasherat.BuildConfig
import com.solutionplus.altasherat.common.presentation.util.Constants
=======
import com.intuit.sdp.BuildConfig
import com.solutionplus.altasherat.common.data.repository.remote.ServiceApi
import com.solutionplus.altasherat.common.data.constants.Constants
>>>>>>> cfb1dac1eda7749c5779221ceab1a6dc04d7939a
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