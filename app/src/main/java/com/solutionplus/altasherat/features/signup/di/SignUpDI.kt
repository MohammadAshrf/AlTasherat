package com.solutionplus.altasherat.features.signup.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.solutionplus.altasherat.features.signup.data.repository.SignupRepository
import com.solutionplus.altasherat.features.signup.data.repository.local.SignupLocalDS
import com.solutionplus.altasherat.features.signup.data.repository.remote.SignupRemoteDS
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import com.solutionplus.altasherat.features.signup.domain.repository.local.ISignupLocalDS
import com.solutionplus.altasherat.common.data.repository.local.DataStoreKeyValueStorage
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC
import com.solutionplus.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS
import com.solutionplus.altasherat.features.signup.domain.usecase.SignupUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object SignUpDI {

    @Provides
    fun provideRemoteDS(provider: INetworkProvider): ISignupRemoteDS = SignupRemoteDS(provider)

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideLocalDS(userPreferences: IKeyValueStorageProvider, dataEncryption: IEncryptionProvider): ISignupLocalDS = SignupLocalDS(userPreferences,dataEncryption)

    @Provides
    fun provideRepository(remoteDS: ISignupRemoteDS, localDS: ISignupLocalDS): ISignupRepository =
        SignupRepository(remoteDS, localDS)


    @Provides
    fun provideLoginWithPhoneUC(repository: ISignupRepository,saveUserUC: SaveUserUC, getUserUC: GetUserUC): SignupUC =
        SignupUC(repository,saveUserUC, getUserUC)

}