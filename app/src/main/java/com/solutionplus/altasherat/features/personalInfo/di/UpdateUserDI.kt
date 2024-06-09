package com.solutionplus.altasherat.features.personalInfo.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personalInfo.data.repository.UpdateProfileRepository
import com.solutionplus.altasherat.features.personalInfo.data.repository.local.UpdateProfileLocalDS
import com.solutionplus.altasherat.features.personalInfo.data.repository.remote.UpdateProfileRemoteDS
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetProfileInfoLocalUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetProfileInfoRemoteUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.UpdateProfileInfoUC
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository
import com.solutionplus.altasherat.features.personalInfo.domain.repository.local.IUpdateProfileLocalDS
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateProfileRemoteDS
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UpdateUserDI {
    @Provides
    fun provideUpdateUserUC(repository: IUpdateProfileRepository, saveUserUC: SaveUserUC): UpdateProfileInfoUC =
        UpdateProfileInfoUC(repository, saveUserUC)

    @Provides
    fun provideGetUserFromRemoteUC(repository: IUpdateProfileRepository): GetProfileInfoRemoteUC =
        GetProfileInfoRemoteUC(repository)

    @Provides
    fun provideRemoteDS(networkProvider: INetworkProvider): IUpdateProfileRemoteDS =
        UpdateProfileRemoteDS(networkProvider)


    @Provides
    fun provideLocalDS(
        localDSProvider: IKeyValueStorageProvider,
        encryptionProvider: IEncryptionProvider
    ): IUpdateProfileLocalDS =
        UpdateProfileLocalDS(localDSProvider, encryptionProvider)


    @Provides
    fun provideRepository(
        localDS: IUpdateProfileLocalDS,
        remoteDS: IUpdateProfileRemoteDS
    ): IUpdateProfileRepository = UpdateProfileRepository(localDS, remoteDS)
}