package com.solutionplus.altasherat.features.profileMenu.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.profileMenu.data.repository.ProfileMenuRepository
import com.solutionplus.altasherat.features.profileMenu.data.repository.local.ProfileMenuDS
import com.solutionplus.altasherat.features.profileMenu.domain.repository.IProfileMenuRepository
import com.solutionplus.altasherat.features.profileMenu.domain.repository.local.IProfileMenuDS
import com.solutionplus.altasherat.features.profileMenu.domain.usecase.CheckUserLoginUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)

internal object profileMenuModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideLocalDS(userPreferences: IKeyValueStorageProvider, dataEncryption: IEncryptionProvider): IProfileMenuDS = ProfileMenuDS(userPreferences,dataEncryption)

    @Provides
    fun provideRepository(localDS: IProfileMenuDS): IProfileMenuRepository =
        ProfileMenuRepository(localDS)


    @Provides
    fun provideCheckUserLoginUC(repository: IProfileMenuRepository): CheckUserLoginUC =
        CheckUserLoginUC(repository)
}