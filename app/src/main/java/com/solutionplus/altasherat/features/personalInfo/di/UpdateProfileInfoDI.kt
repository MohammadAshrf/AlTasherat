package com.solutionplus.altasherat.features.personalInfo.di

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personalInfo.data.repository.UpdateProfileRepository
import com.solutionplus.altasherat.features.personalInfo.data.repository.remote.UpdateProfileRemoteDS
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.GetProfileInfoRemoteUC
import com.solutionplus.altasherat.features.personalInfo.domain.interactor.UpdateProfileInfoUC
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateProfileRemoteDS
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UpdateProfileInfoDI {
    @Provides
    fun provideUpdateProfileInfoUC(
        repository: IUpdateProfileRepository,
        saveUserUC: SaveUserUC
    ): UpdateProfileInfoUC =
        UpdateProfileInfoUC(repository, saveUserUC)

    @Provides
    fun provideProfileInfoRemoteUC(repository: IUpdateProfileRepository): GetProfileInfoRemoteUC =
        GetProfileInfoRemoteUC(repository)

    @Provides
    fun provideRemoteDS(networkProvider: INetworkProvider): IUpdateProfileRemoteDS =
        UpdateProfileRemoteDS(networkProvider)


    @Provides
    fun provideRepository(
        remoteDS: IUpdateProfileRemoteDS
    ): IUpdateProfileRepository = UpdateProfileRepository(remoteDS)
}