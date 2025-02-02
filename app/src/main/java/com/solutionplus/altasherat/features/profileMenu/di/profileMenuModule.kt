package com.solutionplus.altasherat.features.profileMenu.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.solutionplus.altasherat.common.data.repository.remote.interceptors.authInterceptor.AuthTokenProvider
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.profileMenu.data.repository.ProfileMenuRepository
import com.solutionplus.altasherat.features.profileMenu.data.repository.local.ProfileMenuDS
import com.solutionplus.altasherat.features.profileMenu.data.repository.remote.ProfileMenuRemoteDS
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository
import com.solutionplus.altasherat.features.profileMenu.domain.repository.local.IProfileMenuDS
import com.solutionplus.altasherat.features.profileMenu.domain.repository.remote.IProfileMenuRemoteDS
import com.solutionplus.altasherat.features.profileMenu.domain.usecase.CheckUserStateUC
import com.solutionplus.altasherat.features.profileMenu.domain.usecase.GetUserUC
import com.solutionplus.altasherat.features.profileMenu.domain.usecase.LogoutUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)

internal object profileMenuModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideLocalDS(
        userPreferences: IKeyValueStorageProvider,
        dataEncryption: IEncryptionProvider
    ): IProfileMenuDS = ProfileMenuDS(userPreferences, dataEncryption)


    @Provides
    fun provideRemoteDS(provider: INetworkProvider): IProfileMenuRemoteDS =
        ProfileMenuRemoteDS(provider )

    @Provides
    fun provideRepository(
        localDS: IProfileMenuDS,
        remoteDS: IProfileMenuRemoteDS
    ): IProfileMenuRepository =
        ProfileMenuRepository(localDS, remoteDS)


    @Provides
    fun provideGetUserUC(repository: IProfileMenuRepository): GetUserUC =
        GetUserUC(repository)

    @Provides
    fun provideCheckUserStatusUC(repository: IProfileMenuRepository): CheckUserStateUC =
        CheckUserStateUC(repository)

    @Provides
    fun provideLogoutUC(repository: IProfileMenuRepository): LogoutUC =
        LogoutUC(repository)

}