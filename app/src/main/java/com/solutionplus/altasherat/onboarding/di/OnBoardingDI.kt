package com.solutionplus.altasherat.onboarding.di

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
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object OnBoardingDI {
    @Provides
    fun provideIsOnboardingShownUC(repository: IOnBoardingRepository): IsOnboardingShownUC =
        IsOnboardingShownUC(repository)

    @Provides
    fun provideSetOnboardingShownUC(repository: IOnBoardingRepository): SetOnboardingShownUC =
        SetOnboardingShownUC(repository)

    @Provides
    fun provideLocalDS(
        localDSProvider: IKeyValueStorageProvider
    ): IOnBoardingLocalDS =
        OnBoardingLocalDS(localDSProvider)

    @Provides
    fun provideRepository(
        localDS: IOnBoardingLocalDS,
    ): IOnBoardingRepository = OnBoardingRepository(localDS)
}