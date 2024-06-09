package com.solutionplus.altasherat.features.services.user.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.services.user.data.repository.UserRepository
import com.solutionplus.altasherat.features.services.user.data.repository.local.UserLocalDS
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserFromLocalUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository
import com.solutionplus.altasherat.features.services.user.domain.repository.Local.IUserLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UserDI {
    @Provides
    @Singleton
    fun provideSaveUserUC(repository: IUserRepository): SaveUserUC =
        SaveUserUC(repository)

    @Provides
    @Singleton
    fun provideGetUserUC(repository: IUserRepository): GetUserFromLocalUC =
        GetUserFromLocalUC(repository)



    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideLocalDS(
        localDSProvider: IKeyValueStorageProvider,
         encryptionProvider: IEncryptionProvider
    ): IUserLocalDS =
        UserLocalDS(localDSProvider,encryptionProvider)

    @Singleton
    @Provides
    fun provideRepository(
        localDS: IUserLocalDS,
    ): IUserRepository = UserRepository(localDS)
}