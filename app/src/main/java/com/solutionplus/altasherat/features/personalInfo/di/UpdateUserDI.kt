package com.solutionplus.altasherat.features.personalInfo.di

import com.solutionplus.altasherat.common.data.repository.local.encryption.EncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personalInfo.data.repository.UpdateUserRepository
import com.solutionplus.altasherat.features.personalInfo.data.repository.local.UpdateUserLocalDS
import com.solutionplus.altasherat.features.personalInfo.data.repository.remote.UpdateUserRemoteDS
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetUserInfoUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.UpdateUserUC
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository
import com.solutionplus.altasherat.features.personalInfo.domain.repository.local.IUpdateUserLocalDS
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateUserRemoteDS
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UpdateUserDI {
    @Provides
    fun provideUpdateUserUC(repository: IUpdateUserRepository): UpdateUserUC =
        UpdateUserUC(repository)

    @Provides
    fun provideGetUserInfoFromLocalUC(repository: IUpdateUserRepository): GetUserInfoUC =
        GetUserInfoUC(repository)

    @Provides
    fun provideRemoteDS(networkProvider: INetworkProvider): IUpdateUserRemoteDS =
        UpdateUserRemoteDS(networkProvider)


    @Provides
    fun provideLocalDS(
        localDSProvider: IKeyValueStorageProvider,
        encryptionProvider: IEncryptionProvider
    ): IUpdateUserLocalDS =
        UpdateUserLocalDS(localDSProvider, encryptionProvider)


    @Provides
    fun provideRepository(
        localDS: IUpdateUserLocalDS,
        remoteDS: IUpdateUserRemoteDS
    ): IUpdateUserRepository = UpdateUserRepository(localDS, remoteDS)
}