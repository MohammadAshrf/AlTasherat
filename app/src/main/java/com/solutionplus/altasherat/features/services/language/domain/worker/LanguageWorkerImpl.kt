package com.solutionplus.altasherat.features.services.language.domain.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LanguageWorkerImpl @Inject constructor(private val context: Context) {

    suspend fun updateLanguage(language: String): Flow<WorkInfo> = flow {
        val inputData = workDataOf(LanguageWorker.LANGUAGE to language)
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val languageWorkRequest = OneTimeWorkRequestBuilder<LanguageWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniqueWork(
            LanguageWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            languageWorkRequest
        )

        val workInfo = workManager.getWorkInfoByIdFlow(languageWorkRequest.id)
        emitAll(workInfo.filterNotNull())
    }
}