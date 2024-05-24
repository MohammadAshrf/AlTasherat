package com.solutionplus.altasherat.features.services.country.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.login.domain.interactor.login.LoginWithPhoneUC
import com.solutionplus.altasherat.features.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.solutionplus.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import com.solutionplus.altasherat.features.services.country.data.repository.CountriesRepository
import com.solutionplus.altasherat.features.services.country.data.repository.local.CountriesLocalDS
import com.solutionplus.altasherat.features.services.country.data.repository.remote.CountriesRemoteDS
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesFromLocalUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCountriesUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.IsOnboardingShownUC
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS
import com.solutionplus.altasherat.features.signup.data.repository.SignupRepository
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import com.solutionplus.altasherat.features.signup.domain.repository.local.ISignupLocalDS
import com.solutionplus.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS
import com.solutionplus.altasherat.features.signup.domain.usecase.SignupUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object CountriesDI {
    @Provides
    fun provideCountriesUC(repository: ICountriesRepository): GetCountriesUC =
        GetCountriesUC(repository)

    @Provides
    fun provideGetCountriesFromLocalUC(repository: ICountriesRepository): GetCountriesFromLocalUC =
        GetCountriesFromLocalUC(repository)


    @Provides
    fun provideIsOnboardingShownUC(repository: ICountriesRepository): IsOnboardingShownUC =
        IsOnboardingShownUC(repository)

    @Provides
    fun provideRemoteDS(networkProvider: INetworkProvider): ICountriesRemoteDS =
        CountriesRemoteDS(networkProvider)

    @Provides
    fun provideLocalDS(
        localDSProvider: IKeyValueStorageProvider,
    ): ICountriesLocalDS =
        CountriesLocalDS(localDSProvider)

    @Provides
    fun provideRepository(
        localDS: ICountriesLocalDS,
        remoteDS: ICountriesRemoteDS
    ): ICountriesRepository = CountriesRepository(localDS, remoteDS)
}