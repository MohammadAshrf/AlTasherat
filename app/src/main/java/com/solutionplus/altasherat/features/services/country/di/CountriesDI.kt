package com.solutionplus.altasherat.features.services.country.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.services.country.data.repository.CountriesRepository
import com.solutionplus.altasherat.features.services.country.data.repository.local.CountriesLocalDS
import com.solutionplus.altasherat.features.services.country.data.repository.remote.CountriesRemoteDS
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesUC
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CountriesDI {
    @Provides
    @Singleton
    fun provideCountriesUC(repository: ICountriesRepository): GetCountriesUC =
        GetCountriesUC(repository)

    @Singleton
    @Provides
    fun provideGetCountriesFromLocalUC(repository: ICountriesRepository): GetCountriesFromLocalUC =
        GetCountriesFromLocalUC(repository)

    @Singleton
    @Provides
    fun provideRemoteDS(networkProvider: INetworkProvider): ICountriesRemoteDS =
        CountriesRemoteDS(networkProvider)

    @Singleton
    @Provides
    fun provideLocalDS(
        localDSProvider: IKeyValueStorageProvider
    ): ICountriesLocalDS =
        CountriesLocalDS(localDSProvider)

    @Singleton
    @Provides
    fun provideRepository(
        localDS: ICountriesLocalDS,
        remoteDS: ICountriesRemoteDS
    ): ICountriesRepository = CountriesRepository(localDS, remoteDS)
}