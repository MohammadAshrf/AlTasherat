package com.solutionplus.altasherat.features.changepassword.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.changepassword.data.repository.ChangePasswordRepository
import com.solutionplus.altasherat.features.changepassword.data.repository.local.ChangePasswordLocalDS
import com.solutionplus.altasherat.features.changepassword.data.repository.remote.ChangePasswordRemoteDS
import com.solutionplus.altasherat.features.changepassword.domain.repository.IchangePasswordRepository
import com.solutionplus.altasherat.features.changepassword.domain.repository.local.IChangePasswordLocalDS
import com.solutionplus.altasherat.features.changepassword.domain.repository.remote.IChangePasswordRemoteDS
import com.solutionplus.altasherat.features.changepassword.domain.usecase.ChangePasswordUC
import com.solutionplus.altasherat.features.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.login.data.repository.local.LoginLocalDS
import com.solutionplus.altasherat.features.login.data.repository.remote.LoginRemoteDS
import com.solutionplus.altasherat.features.login.domain.interactor.login.LoginWithPhoneUC
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.solutionplus.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object ChangePasswordDI {
    @Provides
    fun provideRemoteDS(provider: INetworkProvider): IChangePasswordRemoteDS = ChangePasswordRemoteDS(provider)

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideLocalDS(userPreferences: IKeyValueStorageProvider, dataEncryption: IEncryptionProvider): IChangePasswordLocalDS = ChangePasswordLocalDS(userPreferences,dataEncryption)

    @Provides
    fun provideRepository(remoteDS: IChangePasswordRemoteDS,localDS: IChangePasswordLocalDS): IchangePasswordRepository =
        ChangePasswordRepository(remoteDS, localDS)

    @Provides
    fun provideChangePasswordUC(repository: IchangePasswordRepository): ChangePasswordUC =
        ChangePasswordUC(repository)

}