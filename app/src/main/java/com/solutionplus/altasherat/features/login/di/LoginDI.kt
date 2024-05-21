package com.solutionplus.altasherat.features.login.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.features.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.login.data.repository.local.LoginLocalDS
import com.solutionplus.altasherat.features.login.data.repository.remote.LoginRemoteDS
import com.solutionplus.altasherat.features.login.domain.interactor.login.LoginWithPhoneUC
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.solutionplus.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object LoginDI {

    @Provides
    fun provideRemoteDS(provider: INetworkProvider): ILoginRemoteDS = LoginRemoteDS(provider)

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideLocalDS(userPreferences: IKeyValueStorageProvider,dataEncryption: IEncryptionProvider): ILoginLocalDS = LoginLocalDS(userPreferences,dataEncryption)

    @Provides
    fun provideRepository(remoteDS: ILoginRemoteDS, localDS: ILoginLocalDS): ILoginRepository =
        LoginRepository(remoteDS, localDS)

    @Provides
    fun provideLoginWithPhoneUC(repository: ILoginRepository): LoginWithPhoneUC =
        LoginWithPhoneUC(repository)

}