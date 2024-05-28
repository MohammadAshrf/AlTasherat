package com.solutionplus.altasherat.features.onboarding.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.onboarding.data.repository.OnBoardingRepository
import com.solutionplus.altasherat.features.onboarding.data.repository.local.OnBoardingLocalDS
import com.solutionplus.altasherat.features.onboarding.domain.interactor.IsOnboardingShownUC
import com.solutionplus.altasherat.features.onboarding.domain.interactor.SetOnboardingShownUC
import com.solutionplus.altasherat.features.onboarding.domain.repository.IOnBoardingRepository
import com.solutionplus.altasherat.features.onboarding.domain.repository.local.IOnBoardingLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingDI {
    @Singleton
    @Provides
    fun provideIsOnboardingShownUC(repository: IOnBoardingRepository): IsOnboardingShownUC =
        IsOnboardingShownUC(repository)

    @Singleton
    @Provides
    fun provideSetOnboardingShownUC(repository: IOnBoardingRepository): SetOnboardingShownUC =
        SetOnboardingShownUC(repository)

    @Singleton
    @Provides
    fun provideLocalDS(
        localDSProvider: IKeyValueStorageProvider
    ): IOnBoardingLocalDS =
        OnBoardingLocalDS(localDSProvider)

    @Singleton
    @Provides
    fun provideRepository(
        localDS: IOnBoardingLocalDS,
    ): IOnBoardingRepository = OnBoardingRepository(localDS)
}