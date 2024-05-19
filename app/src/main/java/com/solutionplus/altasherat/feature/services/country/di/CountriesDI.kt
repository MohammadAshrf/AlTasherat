package com.solutionplus.altasherat.feature.services.country.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.feature.services.country.data.repository.CountriesRepository
import com.solutionplus.altasherat.feature.services.country.data.repository.local.CountriesLocalDS
import com.solutionplus.altasherat.feature.services.country.data.repository.remote.CountriesRemoteDS
import com.solutionplus.altasherat.feature.services.country.domain.interactor.GetCountriesUC
import com.solutionplus.altasherat.feature.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.feature.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.feature.services.country.domain.repository.remote.ICountriesRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object CountriesDI {
    @Provides
    fun provideCountriesUC(repository: ICountriesRepository): GetCountriesUC =
        GetCountriesUC(repository)

    @Provides
    fun provideRemoteDS(networkProvider: INetworkProvider): ICountriesRemoteDS =
        CountriesRemoteDS(networkProvider)

    @Provides
    fun provideLocalDS(
        localDSProvider: IKeyValueStorageProvider,
    ): ICountriesLocalDS =
        CountriesLocalDS(localDSProvider)

    @Provides
    fun provideRepository(
        localDS: ICountriesLocalDS,
        remoteDS: ICountriesRemoteDS
    ): ICountriesRepository = CountriesRepository(localDS, remoteDS)
}