package com.solutionplus.altasherat.features.personalInfo.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personalInfo.data.repository.UpdateUserRepository
import com.solutionplus.altasherat.features.personalInfo.data.repository.local.UpdateUserLocalDS
import com.solutionplus.altasherat.features.personalInfo.data.repository.remote.UpdateUserRemoteDS
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetUserFromLocalUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetUserFromRemoteUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.UpdateUserUC
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository
import com.solutionplus.altasherat.features.personalInfo.domain.repository.local.IUpdateUserLocalDS
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateUserRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UpdateUserDI {
    @Provides
    fun provideUpdateUserUC(repository: IUpdateUserRepository): UpdateUserUC =
        UpdateUserUC(repository)

    @Provides
    fun provideGetUserInfoFromLocalUC(repository: IUpdateUserRepository): GetUserFromLocalUC =
        GetUserFromLocalUC(repository)

    @Provides
    fun provideGetUserFromRemoteUC(repository: IUpdateUserRepository): GetUserFromRemoteUC =
        GetUserFromRemoteUC(repository)

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