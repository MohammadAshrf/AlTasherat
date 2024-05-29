package com.solutionplus.altasherat.features.deleteaccount.di

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
import com.solutionplus.altasherat.features.deleteaccount.data.repository.DeleteAccountRepository
import com.solutionplus.altasherat.features.deleteaccount.data.repository.local.DeleteAccountLocalDS
import com.solutionplus.altasherat.features.deleteaccount.data.repository.remote.DeleteAccountRemoteDS
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.IDeleteAccountRepository
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.local.IDeleteAccountLocalDS
import com.solutionplus.altasherat.features.deleteaccount.domain.repository.remote.IdeleteAccountRemoteDS
import com.solutionplus.altasherat.features.deleteaccount.domain.usecase.DeleteAccountUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object DeleteAccountDI {

    @Provides
    fun provideRemoteDS(provider: INetworkProvider): IdeleteAccountRemoteDS = DeleteAccountRemoteDS(provider)

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideLocalDS(userPreferences: IKeyValueStorageProvider): IDeleteAccountLocalDS = DeleteAccountLocalDS(userPreferences)

    @Provides
    fun provideRepository(localDS: IDeleteAccountLocalDS,remoteDS: IdeleteAccountRemoteDS,): IDeleteAccountRepository =
        DeleteAccountRepository(localDS, remoteDS)

    @Provides
    fun provideDeleteAccountUC(repository: IDeleteAccountRepository): DeleteAccountUC =
        DeleteAccountUC(repository)

}