package com.solutionplus.altasherat.features.services.language.di

import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.services.language.data.repository.LanguageRepository
import com.solutionplus.altasherat.features.services.language.data.repository.local.LanguageLocalDS
import com.solutionplus.altasherat.features.services.language.domain.interactor.GetSelectedCountryUC
import com.solutionplus.altasherat.features.services.language.domain.interactor.SaveSelectedCountryUC
import com.solutionplus.altasherat.features.services.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.services.language.domain.repository.local.ILanguageLocalDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object LanguageDI {
    @Provides
    fun provideSaveSelectedCountryUC(repository: ILanguageRepository): SaveSelectedCountryUC =
        SaveSelectedCountryUC(repository)

    @Provides
    fun provideGetSelectedCountryUC(repository: ILanguageRepository): GetSelectedCountryUC =
        GetSelectedCountryUC(repository)

    @Provides
    fun provideLocalDS(
        localDSProvider: IKeyValueStorageProvider
    ): ILanguageLocalDS =
        LanguageLocalDS(localDSProvider)

    @Provides
    fun provideRepository(
        localDS: ILanguageLocalDS,
    ): ILanguageRepository = LanguageRepository(localDS)
}